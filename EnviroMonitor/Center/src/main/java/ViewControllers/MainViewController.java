package ViewControllers;

import Components.NotificationListCell;
import Components.RegionalListCell;
import Components.StationListCell;
import Controllers.EnvironmentalCentreImpl;
import Controllers.OrbRunner;
import Environment.Reading;
import Environment.RegionalCentre;
import Environment.RegionalCentreHelper;
import Interfaces.IIORProvider;
import Models.CenterToRecipientRecord;
import Models.RegionalCenterRecord;
import Models.Settings;
import Utils.MailService;
import Utils.StateStorage;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main controller for Environmental centre gui
 */
public class MainViewController {
    @FXML
    private ListView<Reading> stationList;
    @FXML
    private ListView<RegionalCenterRecord> regionalList;
    @FXML
    private ListView<CenterToRecipientRecord> emailList;
    @FXML
    private Button emailAddButton;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField emailInput;

    private List<CenterToRecipientRecord> notificationList;
    private ObservableList<CenterToRecipientRecord> emailObservableList;
    private ObservableList<Reading> readingObservableList;
    private ORB orb;
    private NamingContextExt namingService;
    private static final Logger logger = LogManager.getLogger(MainViewController.class.getName());

    public MainViewController(String[] args, IIORProvider provider) throws IOException {
        orb = ORB.init(args, null);
        //get reference to the naming service
        org.omg.CORBA.Object nameServiceObj =
                orb.string_to_object(provider.GetIOR());
        if (nameServiceObj == null) {
            System.out.println("nameServiceObj = null");
            return;
        }
        namingService = NamingContextExtHelper.narrow(nameServiceObj);
    }

    public void shutdown() {
        orb.shutdown(false);
    }

    @FXML
    public void initialize() {
        //initialize all the lists
        Settings settings = Settings.getInstance();
        readingObservableList = FXCollections.observableArrayList();
        stationList.setItems(readingObservableList);
        stationList.setCellFactory(param -> new StationListCell(namingService, this));
        emailObservableList = FXCollections.observableArrayList();
        emailList.setItems(emailObservableList);
        emailList.setCellFactory(param -> new NotificationListCell(this));
        ObservableList<RegionalCenterRecord> regionalCenterRecordObservableList;
        //If loading parameter specified try to load previous program state from files
        if (settings.shouldLoad()) {
            try {
                regionalCenterRecordObservableList = StateStorage.loadCenterList("centerRecords.out");
                notificationList = StateStorage.loadRecipients("recipients.out");
            } catch (Exception e) {
                //If loading fails initialize clean state
                regionalCenterRecordObservableList = FXCollections.observableArrayList();
                notificationList = new ArrayList<>();
                logger.error("Failed to load previous state", e);
            }
        } else {
            regionalCenterRecordObservableList = FXCollections.observableArrayList();
            notificationList = new ArrayList<>();
        }
        regionalList.setItems(regionalCenterRecordObservableList);
        regionalList.setCellFactory(param -> new RegionalListCell());
        //bind add agency button to be enabled only when regional centre is selected and all fields are filed
        BooleanBinding booleanBind = nameInput.textProperty().isEmpty()
                .or(emailInput.textProperty().isEmpty())
                .or(regionalList.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        emailAddButton.disableProperty().bind(booleanBind);

        emailAddButton.setOnMouseClicked(event -> {
            RegionalCenterRecord item = regionalList.getSelectionModel().getSelectedItem();
            if (item != null) {
                addNewRecipient(nameInput.getText(), emailInput.getText(), item.getName());
            }
        });

        regionalList.setOnMouseClicked(event -> {
            RegionalCenterRecord value = regionalList.getSelectionModel().getSelectedItem();
            value.resetAlarm();
            regionalList.refresh();
            updateStationReadingList(value.getName());
            updateNotificationList(value.getName());
        });

        MailService mailService = new MailService(notificationList);
        EnvironmentalCentreImpl centre = new EnvironmentalCentreImpl(regionalCenterRecordObservableList, regionalList, this,
                mailService);
        OrbRunner orbRunner = new OrbRunner(orb, namingService, centre);
        orbRunner.start();
    }

    /**
     * Update list of readings to readings from specified regional centre
     *
     * @param name - regional centre name
     */
    public void updateStationReadingList(String name) {
        readingObservableList.clear();
        try {
            RegionalCentre center = RegionalCentreHelper.narrow(namingService.resolve_str(name));
            readingObservableList.addAll(center.take_readings());
        } catch (Exception e) {
            logger.error("Error retrieving reading list from center " + name, e);
        }
    }

    /**
     * Update reading list for currently selected regional centre
     */
    public void updateStationReadingList() {
        RegionalCenterRecord item = regionalList.getSelectionModel().getSelectedItem();
        if (item != null) {
            updateStationReadingList(item.getName());
        }
    }

    /**
     * Update list of notification recipients for currently selected regional centre
     */
    public void updateNotificationList() {
        RegionalCenterRecord item = regionalList.getSelectionModel().getSelectedItem();
        if (item != null) {
            updateNotificationList(item.getName());
        }
    }

    /**
     * Update list of notification receivers for specific regional centre
     *
     * @param name - name of regional centre
     */
    private void updateNotificationList(String name) {
        emailObservableList.clear();
        notificationList.stream().filter(x -> x.getCenterName().equals(name)).forEach(x -> emailObservableList.add(x));
    }

    /**
     * Remove notification receiver from the list
     *
     * @param record - record that should be removed
     */
    public void removeRecipient(CenterToRecipientRecord record) {
        notificationList.remove(record);
        updateNotificationList();
        try {
            StateStorage.saveRecipients(notificationList, "recipients.out");
        } catch (IOException e) {
            logger.error("Failed to save recipient list", e);
        }
    }

    /**
     * Add new notification receiver
     * @param who - name of the receiver
     * @param email - email where notification will be sent
     * @param region_of_interest - name of regional centre that receiver is interested in
     */
    public void addNewRecipient(String who, String email, String region_of_interest) {
        notificationList.add(new CenterToRecipientRecord(region_of_interest, who, email));
        updateNotificationList();
        try {
            StateStorage.saveRecipients(notificationList, "recipients.out");
        } catch (IOException e) {
            logger.error("Failed to save recipient list", e);
        }
    }
}
