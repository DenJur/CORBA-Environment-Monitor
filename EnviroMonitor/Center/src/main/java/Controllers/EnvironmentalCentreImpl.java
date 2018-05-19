package Controllers;

import Environment.EnvironmentalCentrePOA;
import Environment.Reading;
import Models.RegionalCenterRecord;
import Utils.MailService;
import Utils.StateStorage;
import ViewControllers.MainViewController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Implementation of IDL POA class for EnvironmentalCentre
 */
public class EnvironmentalCentreImpl extends EnvironmentalCentrePOA {

    private MailService mailService;
    private MainViewController controller;
    private ObservableList<RegionalCenterRecord> recordList;
    private ListView<RegionalCenterRecord> listView;
    private static final Logger logger = LogManager.getLogger(EnvironmentalCentreImpl.class.getName());
    private static final Object lock=new Object();

    public EnvironmentalCentreImpl(ObservableList<RegionalCenterRecord> regionalCenterRecordObservableList,
                                   ListView<RegionalCenterRecord> regionalList, MainViewController mainViewController,
                                   MailService mail) {
        recordList = regionalCenterRecordObservableList;
        listView = regionalList;
        controller = mainViewController;
        mailService = mail;
    }

    /**
     * Called by regional centres to triggeren an alarm in environmental centre
     * @param center_name - name of the regional centre that triggered the alarm
     * @param readings_list - list of readings that triggered the alarm
     */
    @Override
    public void raise_alarm(String center_name, Reading[] readings_list) {
        synchronized (lock) {
            //find centre that triggered the alarm in registered centre list
            FilteredList<RegionalCenterRecord> center = recordList.filtered(regionalCenterRecord ->
                    regionalCenterRecord.getName().equals(center_name));
            if (!center.isEmpty()) {
                RegionalCenterRecord record = center.get(0);
                //trigger the alarm if it is not already triggered
                if (!record.getAlarm()) {
                    record.triggerAlarm();
                    Platform.runLater(() -> {
                        listView.refresh();
                        //refresh reading list if user is viewing the centre
                        if (listView.getSelectionModel().getSelectedItem().getName().equals(center_name)) {
                            listView.getSelectionModel().select(record);
                            controller.updateStationReadingList();
                            controller.updateNotificationList();
                        }
                        mailService.sendAlarmEmail(record.getName());
                    });
                }
            }
        }
    }

    /**
     * Add agency to list of notifications
     * @param who - name of the agency
     * @param email - email to send the notification to
     * @param region_of_interest - name of the regional centre that agency is interested in
     */
    @Override
    public void register_agency(String who, String email, String region_of_interest) {
        controller.addNewRecipient(who, email, region_of_interest);
    }

    /**
     * Register regional centre with environmental centre
     * @param centre_name - name of regional centre to register
     */
    @Override
    public void register_regional_centre(String centre_name) {
        synchronized (lock) {
            //only add if centre isn't already registered
            if (recordList.filtered(regionalCenterRecord -> regionalCenterRecord.getName().equals(centre_name)).isEmpty())
                Platform.runLater(() -> {
                    recordList.add(new RegionalCenterRecord(centre_name));
                    //save new regional centre list to disk
                    try {
                        StateStorage.saveCenterList(recordList, "centerRecords.out");
                    } catch (IOException e) {
                        logger.warn("Failed to save regional centre list", e);
                    }
                });
        }
    }
}
