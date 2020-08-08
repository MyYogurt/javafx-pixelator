package pixelator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ValidValuesController {

    @FXML
    private ListView<Integer> valuesList;

    private Stage stage;

    /**
     * Sets the list of valid block count values
     * @param list Valid values
     */
    public void setList(ArrayList<Integer> list) {
        valuesList.setItems(FXCollections.observableArrayList(list));
    }

    /**
     * Sets the stage. Used to close the stage when the close button is clicked
     * @param stage stage
     * @param xcoord x coordinate of update block count window
     * @param ycoord y coordinate of update block count window
     */
    public void setStage(Stage stage, int xcoord, int ycoord) {
        this.stage = stage;
        this.stage.setX(xcoord+300);
        this.stage.setY(ycoord-125);
    }

    /**
     * Closes the valid values window
     */
    public void closeWindow() {
        stage.close();
    }
}
