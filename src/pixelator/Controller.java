package pixelator;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.Arrays;

public class Controller {

    private Stage mainStage;

    /**
     * Passes main window to this controller
     * @param mainStage main stage
     */
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    /**
     * Opens a file dialog to get new image and initializes program
     */
    public void buttonGetImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", Arrays.asList("*.png", "*.jpg", "*.jpeg"))
        );
        File userFile = fileChooser.showOpenDialog(null);
        if (userFile != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("pixelatorLoaded.fxml"));
                Parent root = loader.load();
                PixelatorLoadedController controller = loader.getController();
                controller.setMainStage(mainStage);
                controller.setImage(new Image(userFile.toURI().toURL().toString()));
                controller.pixelate();
                mainStage.setScene(new Scene(root, 600, 400));
            } catch (Exception ioex) {
                ioex.printStackTrace();
            }
        }
    }
}
