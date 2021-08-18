package driver;

import controller.ScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Calculator;

import java.io.IOException;
import java.util.Objects;

public class Driver extends Application {
    private static ScreenController screenController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Payroll.fxml")));

        Scene scene = new Scene(root);
        initScreenController(scene);

        primaryStage.setScene(scene);
        primaryStage.setWidth(840);
        primaryStage.setHeight(540);

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
        screenController.addScreen("Payroll", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Payroll.fxml"))));

        screenController.addScreen("EditFees", FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/EditFees.fxml"))));
    }

    public static ScreenController getScreenController() {
        return screenController;
    }
}
