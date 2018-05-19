package Components;

import Models.RegionalCenterRecord;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Cell view for list of regional centres
 */
public class RegionalListCell extends ListCell<RegionalCenterRecord> {
    @FXML
    private HBox hBox;
    @FXML
    private Label label;
    @FXML
    private FontAwesomeIconView icon;
    private RegionalCenterRecord record;
    private static final Logger logger = LogManager.getLogger(RegionalListCell.class.getName());

    public RegionalListCell()
    {
        //initialize gui from the fxml
        FXMLLoader fxmlLoader = new FXMLLoader(RegionalListCell.class.getResource("/Views/RegionalCenterCellView.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            logger.warn("Was unable to load fxml for RegionalListCell",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(RegionalCenterRecord record, boolean empty)
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
        this.label.setText(record.getName());
        //Set different styles depending on if regional centre currently has an alarm or not
        if(record.getAlarm()){
            this.label.setTextFill(Color.RED);
            this.label.setFont(Font.font("System", FontWeight.BOLD,16));
            this.icon.setIcon(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
            this.icon.setFill(Color.RED);
        }
        else {
            this.label.setTextFill(Color.BLACK);
            this.label.setFont(Font.font("System",16));
            this.icon.setIcon(FontAwesomeIcon.CHECK_CIRCLE);
            this.icon.setFill(Color.GREEN);
        }

    }
}
