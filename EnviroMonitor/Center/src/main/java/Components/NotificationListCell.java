package Components;

import Models.CenterToRecipientRecord;
import ViewControllers.MainViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Cell view for list of agencies that need to be notified in case of alarm
 */
public class NotificationListCell extends ListCell<CenterToRecipientRecord> {
    @FXML
    private HBox hBox;
    @FXML
    private Label label;
    @FXML
    private Button removeButton;
    private CenterToRecipientRecord record;
    private MainViewController controller;
    private static final Logger logger = LogManager.getLogger(NotificationListCell.class.getName());

    public NotificationListCell(MainViewController controller)
    {
        //initialize gui from the fxml
        this.controller=controller;
        FXMLLoader fxmlLoader = new FXMLLoader(NotificationListCell.class.getResource("/Views/NotificationCellView.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            logger.warn("Was unable to load fxml for NotificationCellView",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(CenterToRecipientRecord record, boolean empty)
    {
        //clean the view
        setText(null);
        setGraphic(null);
        super.updateItem(record, empty);
        //update if its not empty
        if(record != null && !empty)
        {
            this.record=record;
            updateView();
            setGraphic(hBox);
        }
    }

    private void updateView()
    {
        this.label.setText(record.getRecipientName()+" - "+record.getRecipientEmail());
        this.removeButton.setOnMouseClicked(event-> controller.removeRecipient(record));
    }
}
