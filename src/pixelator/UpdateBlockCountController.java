package pixelator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateBlockCountController {

    @FXML
    private TextField textField;

    @FXML
    private Label label;

    private PixelatorLoadedController pixelatorLoadedController;

    private Stage updateBlockCountStage;

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
}
