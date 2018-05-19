package ViewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import Interfaces.ISensor;

/**
 * Implementation of sensor interface. Uses JavaFX to provide GUI that user can interact with.
 */
public class MainViewController implements ISensor {
    @FXML
    private Slider readingSlider;

    @Override
    public float getReading() {
        return (float)readingSlider.getValue();
    }

    @Override
    public void reset() {
        readingSlider.setValue(0);
    }
}
