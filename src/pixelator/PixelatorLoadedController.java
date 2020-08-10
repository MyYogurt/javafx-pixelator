package pixelator;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class PixelatorLoadedController {

    @FXML
    private ImageView imageView1, imageView2;

    private final ContextMenu contextMenu = new ContextMenu();

    private Stage mainStage;

    private ArrayList<Integer> validBlockSizeValues;

    private int blockCount, width, height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Passes the main stage to this controller
     * @param stage main window
     */
    public void setMainStage(Stage stage) {
        mainStage = stage;
        MenuItem loadNewImage = new MenuItem("Load New Image");
        MenuItem savePixelatedImage = new MenuItem("Save Pixelated Image");
        MenuItem updateBlockCount = new MenuItem("Update Block Count");
        loadNewImage.setOnAction((event) -> {
            getNewImage();
        });
        savePixelatedImage.setOnAction((event) -> {
            savePixelatedImage();
        });
        updateBlockCount.setOnAction((event) -> {
            updateBlockCount();
        });
        contextMenu.getItems().addAll(loadNewImage, savePixelatedImage, updateBlockCount);
        imageView1.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                contextMenu.show(imageView1, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }
        });
        imageView1.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            contextMenu.hide();
        });
        imageView2.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                contextMenu.show(imageView2, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }
        });
        imageView2.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            contextMenu.hide();
        });
    }

    /**
     * Updates the image
     * @param path Image
     */
    public void setImage(Image path) {
        imageView1.setImage(path);
        width = (int) path.getWidth();
        height = (int) path.getHeight();
        int tempBlockSize = 10;
        while (!Pixelate.verifyBlockCount(width, height, tempBlockSize))
            tempBlockSize--;
        blockCount = tempBlockSize;
        Task<Void> getValidValues = new Task<Void>() {
            @Override
            public Void call() {
                ArrayList<Integer> validValues = new ArrayList<Integer>();
                int inc = width % 2 == 0 ? 1 : 2;
                for (int counter = 1; counter < width; counter += inc) {
                    if(width % counter == 0 && height % counter ==0)
                        validValues.add(counter);
                }
                validBlockSizeValues = validValues;
                return null;
            }
        };
        new Thread(getValidValues).start();
        pixelate();
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
        Task<Void> pixelateImage = new Task<Void>() {
            @Override
            public Void call() {
                BufferedImage pixelated = Pixelate.pixelate(SwingFXUtils.fromFXImage(imageView1.getImage(), null), blockCount);
                imageView2.setImage(SwingFXUtils.toFXImage(pixelated, null));
                return null;
            }
        };
        new Thread(pixelateImage).start();
    }

    /**
     * Updates the block count and repixelates
     */
    public void updateBlockCount() {
        FXMLLoader updateCountLoader = new FXMLLoader(getClass().getResource("updateBlockCount.fxml"));
        Stage updateCountStage = new Stage();
        updateCountStage.initModality(Modality.WINDOW_MODAL);
        updateCountStage.initOwner(mainStage);
        updateCountStage.setTitle("Update Block Count");
        updateCountStage.setResizable(false);
        try {
            Parent root = updateCountLoader.load();
            UpdateBlockCountController updateBlockCountController = updateCountLoader.getController();
            updateBlockCountController.setPixelatorLoadedController(this);
            updateBlockCountController.setStage(updateCountStage);
            updateBlockCountController.setValidValues(validBlockSizeValues);
            updateBlockCountController.setLabel("The dimensions of your image are: "+width+" x "+height+"\nYou can set a new block count bellow. A smaller block count will result in a more pixelated image. The block count must evenly divide into both the width and height of your image.");
            updateCountStage.setScene(new Scene(root, 300, 150));
            updateCountStage.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets the block count
     * @param blockCount
     */
    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
        pixelate();
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
                setImage(new Image(userFile.toURI().toURL().toString()));
            } catch (Exception ioex) {
                Alert blockSizeError = new Alert(Alert.AlertType.ERROR);
                blockSizeError.setTitle("Error");
                blockSizeError.setHeaderText("Error");
                blockSizeError.setContentText("Error opening new image");
                blockSizeError.showAndWait();
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
