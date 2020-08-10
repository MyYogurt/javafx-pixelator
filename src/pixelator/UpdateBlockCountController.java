package pixelator;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UpdateBlockCountController {

    @FXML
    private Label label;

    @FXML
    private ListView<Integer> valuesList;

    private PixelatorLoadedController pixelatorLoadedController;

    private Stage updateBlockCountStage;

    public void setData(UpdateBlockCountData data) {
        updateBlockCountStage = data.getUpdateCountStage();
        label.setText(data.getLabelText());
        pixelatorLoadedController = data.getPixelatorLoadedController();
        valuesList.setItems(FXCollections.observableArrayList(data.getValidValues()));
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

    public void updateBlockCountWithInt(int blockCount) {
        try {
            pixelatorLoadedController.setBlockCount(blockCount);
            updateBlockCountStage.close();
        } catch (Exception ex) {
            pixelatorLoadedController.showBlockSizeError();
        }
    }
}
