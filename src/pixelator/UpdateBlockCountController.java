package pixelator;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UpdateBlockCountController {

    @FXML
    private Label label;

    @FXML
    private ListView<Integer> valuesList;

    private PixelatorLoadedController pixelatorLoadedController;

    private Stage updateBlockCountStage;

    public void setValidValues(ArrayList<Integer> list) {
        valuesList.setItems(FXCollections.observableArrayList(list));
        valuesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    Integer currentValue = valuesList.getSelectionModel().getSelectedItem();
                    updateBlockCountStage.close();
                    updateBlockCountWithInt(currentValue);
                }
            }
        });
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

    public void updateBlockCountWithInt(int blockCount) {
        try {
            pixelatorLoadedController.setBlockCount(blockCount);
            updateBlockCountStage.close();
        } catch (Exception ex) {
            pixelatorLoadedController.showBlockSizeError();
        }
    }
}
