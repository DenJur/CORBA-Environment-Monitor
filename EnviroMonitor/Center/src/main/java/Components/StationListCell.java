package Components;

import Environment.MonitoringStation;
import Environment.MonitoringStationHelper;
import Environment.Reading;
import ViewControllers.MainViewController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CosNaming.NamingContextExt;

import java.io.IOException;

/**
 * Cell view for list of monitoring station readings
 */
public class StationListCell extends ListCell<Reading> {
    @FXML
    private HBox hBox;
    @FXML
    private Label label;
    @FXML
    private FontAwesomeIconView icon;
    @FXML
    private Button onOffButton;
    @FXML
    private Button resetButton;
    private Reading reading;
    private final MainViewController controller;
    private NamingContextExt namingService;
    private static final Logger logger = LogManager.getLogger(StationListCell.class.getName());

    public StationListCell(NamingContextExt namingService, MainViewController mainViewController) {
        //initialize gui from the fxml
        FXMLLoader fxmlLoader = new FXMLLoader(RegionalListCell.class.getResource("/Views/ReadingCellView.fxml"));
        fxmlLoader.setController(this);
        this.namingService = namingService;
        this.controller = mainViewController;
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            logger.warn("Was unable to load fxml for StationListCell", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(Reading reading, boolean empty) {
        //clean the view
        setText(null);
        setGraphic(null);
        super.updateItem(reading, empty);
        //update if its not empty
        if (reading != null && !empty) {
            this.reading = reading;
            updateReading();
            setGraphic(hBox);
        }
    }

    private void updateReading() {
        this.label.setText(reading.station_name + " - " + reading.location + " - " + reading.group + " - " + reading.reading_value);
        //make a network call on reset button press
        this.resetButton.setOnMouseClicked(event -> {
            try {
                MonitoringStation station = MonitoringStationHelper.narrow(namingService.resolve_str(reading.station_name));
                station.reset();
                controller.updateStationReadingList();
            } catch (Exception e) {
                logger.warn("Was unable to get station reading after the reset for station " + reading.station_name, e);
            }
        });
        //apply different text style if reading is above alarm threshold
        if (reading.reading_value >= reading.alarm_level) {
            this.label.setTextFill(Color.RED);
            this.label.setFont(Font.font("System", FontWeight.BOLD, 16));
        } else {
            this.label.setTextFill(Color.BLACK);
            this.label.setFont(Font.font("System", 16));
        }

        //display different icon dependent on station status
        if (!reading.status) {
            this.icon.setIcon(FontAwesomeIcon.TIMES_RECTANGLE);
            this.icon.setFill(Color.GOLD);
        } else if (reading.reading_value >= reading.alarm_level) {
            this.icon.setIcon(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
            this.icon.setFill(Color.RED);
        } else {
            this.icon.setIcon(FontAwesomeIcon.CHECK_CIRCLE);
            this.icon.setFill(Color.GREEN);
        }

        if (reading.status) {
            buttonSetEnabled();
            onOffButton.setText("Disable");
        } else {
            buttonSetDisabled();
            onOffButton.setText("Enable");
        }
    }

    /**
     * Handles enable button click
     */
    private void buttonSetEnabled() {
        this.onOffButton.setOnMouseClicked(event -> {
            try {
                MonitoringStation station = MonitoringStationHelper.narrow(namingService.resolve_str(reading.station_name));
                station.turn_off();
                buttonSetDisabled();
                controller.updateStationReadingList();
                onOffButton.setText("Disable");
            } catch (Exception e) {
                logger.warn("Was unable to turn station " + reading.station_name + " on", e);
            }
        });
    }

    /**
     * Handle disable button click
     */
    private void buttonSetDisabled() {
        this.onOffButton.setOnMouseClicked(event -> {
            try {
                MonitoringStation station = MonitoringStationHelper.narrow(namingService.resolve_str(reading.station_name));
                station.turn_on();
                buttonSetEnabled();
                controller.updateStationReadingList();
                onOffButton.setText("Enable");
            } catch (Exception e) {
                logger.warn("Was unable to turn station " + reading.station_name + " off", e);
            }
        });
    }
}
