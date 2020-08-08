package pixelator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UpdateBlockCountController {

    @FXML
    private TextField textField;

    @FXML
    private Label label;

    private PixelatorLoadedController pixelatorLoadedController;

    private Stage updateBlockCountStage;

    private ArrayList<Integer> validValues;

    public void setValidValues(ArrayList<Integer> list) {
        validValues = list;
    }

    public void setStage(Stage stage) {
        updateBlockCountStage = stage;
    }

    public void setPixelatorLoadedController(PixelatorLoadedController pixelatorLoadedController) {
        this.pixelatorLoadedController = pixelatorLoadedController;
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public void updateBlockCount() {
        try {
            pixelatorLoadedController.setBlockCount(Integer.parseInt(textField.getText()));
            updateBlockCountStage.close();
        } catch (Exception ex) {
            pixelatorLoadedController.showBlockSizeError();
        }
    }

    public void viewValidBlockCounts() {
        try {
            FXMLLoader valuesLoader = new FXMLLoader(getClass().getResource("validValues.fxml"));
            Parent root = valuesLoader.load();
            ValidValuesController controller = valuesLoader.getController();
            controller.setList(validValues);
            Stage validValuesStage = new Stage();
            validValuesStage.setTitle("Valid Block Count Values");
            controller.setStage(validValuesStage, (int) updateBlockCountStage.getX(), (int) updateBlockCountStage.getY());
            validValuesStage.initModality(Modality.WINDOW_MODAL);
            validValuesStage.setScene(new Scene(root, 300, 400));
            validValuesStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
