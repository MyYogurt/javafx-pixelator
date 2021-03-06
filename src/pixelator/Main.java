package pixelator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/pixelator.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setMainStage(primaryStage);
        primaryStage.setTitle("Java Pixelator");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
