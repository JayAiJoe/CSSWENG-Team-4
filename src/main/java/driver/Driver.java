package driver;

import controller.ScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Calculator;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Driver extends Application {
    private static ScreenController screenController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL url = new File("src/main/resources/fxml/Payroll.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        Scene scene = new Scene(root);
        initScreenController(scene);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(event -> Calculator.getInstance().close());

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void initScreenController(Scene main) throws IOException {
        // create ScreenController object
        screenController = new ScreenController(main);

        // add screens
        screenController.addScreen("Payroll", FXMLLoader.load(
                new File("src/main/resources/fxml/Payroll.fxml").toURI().toURL()));

        screenController.addScreen("EditFees", FXMLLoader.load(
                new File("src/main/resources/fxml/EditFees.fxml").toURI().toURL()));
    }

    public static ScreenController getScreenController() {
        return screenController;
    }
}
