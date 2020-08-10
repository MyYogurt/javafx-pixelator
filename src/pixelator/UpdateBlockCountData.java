package pixelator;

import javafx.stage.Stage;

import java.util.ArrayList;

public class UpdateBlockCountData {

    private PixelatorLoadedController pixelatorLoadedController;

    private Stage updateCountStage;

    private ArrayList<Integer> validValues;

    private String labelText;

    public UpdateBlockCountData(PixelatorLoadedController controller, Stage stage, ArrayList<Integer> values, String text) {
        pixelatorLoadedController = controller;
        updateCountStage = stage;
        validValues = values;
        labelText = text;
    }

    public ArrayList<Integer> getValidValues() {
        return validValues;
    }

    public Stage getUpdateCountStage() {
        return  updateCountStage;
    }

    public String getLabelText() {
        return labelText;
    }

    public PixelatorLoadedController getPixelatorLoadedController() {
        return pixelatorLoadedController;
    }
}
