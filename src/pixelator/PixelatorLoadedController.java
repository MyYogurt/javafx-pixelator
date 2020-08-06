package pixelator;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class PixelatorLoadedController {

    @FXML
    private ImageView imageView1, imageView2;

    @FXML
    private Label label;

    @FXML
    private TextField textField;

    private Stage mainStage;

    private int blockCount, width, height;

    /**
     * Passes the main stage to this controller
     * @param stage main window
     */
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    /**
     * Updates the image
     * @param path Image
     */
    public void setImage(Image path) {
        imageView1.setImage(path);
        label.setText("The dimensions of the photo are: "+path.getWidth()+" x "+path.getHeight()+" You can set a new block count below. A smaller block count will result in a more pixelated image. The block count must evenly divide into both the width and the height of the image");
        width = (int) path.getWidth();
        height = (int) path.getHeight();
        int tempBlockSize = 10;
        while (!Pixelate.verifyBlockSize(width, height, tempBlockSize))
            tempBlockSize++;
        blockCount = tempBlockSize;
    }

    /**
     * Exits program
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Shows an alert with information about the program
     */
    public void showAbout() {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("Java Pixelator");
        about.setContentText("Pixelates images.");
        about.showAndWait();
    }

    /**
     * Pixelates the current image and displays it
     */
    public void pixelate() {
        BufferedImage pixelated = Pixelate.pixelate(SwingFXUtils.fromFXImage(imageView1.getImage(), null), blockCount);
        imageView2.setImage(SwingFXUtils.toFXImage(pixelated, null));
    }

    /**
     * Updates the block count and repixelates
     */
    public void updateBlockCount() {
        try {
            int tempBlockSize = Integer.parseInt(textField.getText());
            if (!Pixelate.verifyBlockSize(width, height, tempBlockSize))
                showBlockSizeError();
            else {
                blockCount = tempBlockSize;
                pixelate();
            }
        } catch (NumberFormatException numbex) {
            showBlockSizeError();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Shows an alert that the block size was invalid
     */
    public void showBlockSizeError() {
        Alert blockSizeError = new Alert(Alert.AlertType.ERROR);
        blockSizeError.setTitle("Error");
        blockSizeError.setHeaderText("Error");
        blockSizeError.setContentText("Invalid block size.");
        blockSizeError.showAndWait();
    }

    /**
     * Shows an alert that the program was unable to save the image
     */
    public void showSaveError() {
        Alert blockSizeError = new Alert(Alert.AlertType.ERROR);
        blockSizeError.setTitle("Error");
        blockSizeError.setHeaderText("Error");
        blockSizeError.setContentText("Error saving image");
        blockSizeError.showAndWait();
    }

    /**
     * Opens a file dialog to select a new image
     */
    public void getNewImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", Arrays.asList("*.png", "*.jpg", "*.jpeg"))
        );
        fileChooser.setTitle("Open Image");
        File userFile = fileChooser.showOpenDialog(null);
        if (userFile != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("pixelatorLoaded.fxml"));
                Parent root = loader.load();
                PixelatorLoadedController controller = loader.getController();
                controller.setImage(new Image(userFile.toURI().toURL().toString()));
                controller.pixelate();
                mainStage.setScene(new Scene(root, 600, 400));
            } catch (Exception ioex) {
                ioex.printStackTrace();
            }
        }
    }

    /**
     * Opens a file dialog to save the pixelated image
     */
    public void savePixelatedImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Pixelated Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", Arrays.asList("*.png", "*.jpg", "*.jpeg")));
        File saveFile = fileChooser.showSaveDialog(null);
        if (saveFile != null) {
            try {
                String format = Pixelate.getFileExtension(saveFile.toURI().toURL().toString());
                BufferedImage pixelatedImage = SwingFXUtils.fromFXImage(imageView2.getImage(), null);
                ImageIO.write(pixelatedImage, format, saveFile);
            } catch (Exception ex) {
                showSaveError();
            }
        }
    }
}
