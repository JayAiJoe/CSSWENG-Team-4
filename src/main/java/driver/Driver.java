package driver;

import controller.ScreenController;
import dao.Repository;
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

    public static final int WIDTH = 1300;
    public static final int HEIGHT = 780;
    private boolean reMaximize = false;

    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/NavBar.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(getClass().getResource("/css/style.css").toExternalForm());
        initScreenController(scene);
        screenController.activate("Home");

        try {
            Repository.getInstance();
        } catch (Exception e) {
            // TODO: Error establishing connection to db
            System.out.println("Error establishing connection to the database!");
        }


        primaryStage.setScene(scene);
        primaryStage.setMinWidth(WIDTH);
        primaryStage.setMaxWidth(WIDTH);
        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setMaxHeight(HEIGHT);

        primaryStage.maximizedProperty().addListener((observer, oldVal, newVal) -> {
            if (newVal) {
                primaryStage.setMaxWidth(Integer.MAX_VALUE);
                primaryStage.setMaxHeight(Integer.MAX_VALUE);
                if (!reMaximize) {
                    reMaximize = true;
                    primaryStage.setMaximized(false);
                    primaryStage.setMaximized(true);
                }
                reMaximize = false;
            }
            else {
                if (!reMaximize) {
                    primaryStage.setMaxWidth(WIDTH);
                    primaryStage.setMaxHeight(HEIGHT);
                }
            }
        });

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
        FXMLLoader navBarLoader = new FXMLLoader();
        navBarLoader.setLocation(getClass().getResource("/fxml/NavBar.fxml"));
        screenController.addScreen("NavBar", navBarLoader);

        FXMLLoader payrollLoader = new FXMLLoader();
        payrollLoader.setLocation(getClass().getResource("/fxml/Payroll.fxml"));
        screenController.addScreen("Payroll", payrollLoader);

        FXMLLoader editFeesLoader = new FXMLLoader();
        editFeesLoader.setLocation(getClass().getResource("/fxml/EditFees.fxml"));
        screenController.addScreen("EditFees", editFeesLoader);

        FXMLLoader homeLoader = new FXMLLoader();
        homeLoader.setLocation(getClass().getResource("/fxml/Home.fxml"));
        screenController.addScreen("Home", homeLoader);

        FXMLLoader pendingOvertimeLoader = new FXMLLoader();
        pendingOvertimeLoader.setLocation(getClass().getResource("/fxml/OvertimeManagement.fxml"));
        screenController.addScreen("PendingOvertime", pendingOvertimeLoader);

        FXMLLoader approvedOvertimeLoader = new FXMLLoader();
        approvedOvertimeLoader.setLocation(getClass().getResource("/fxml/ApprovedOvertimeWorkHours.fxml"));
        screenController.addScreen("ApprovedOvertimeWorkHours", approvedOvertimeLoader);

        FXMLLoader attendanceStatisticsLoader = new FXMLLoader();
        attendanceStatisticsLoader.setLocation(getClass().getResource("/fxml/AttendanceStatistics.fxml"));
        screenController.addScreen("AttendanceStatistics", attendanceStatisticsLoader);

        FXMLLoader employeesLoader = new FXMLLoader();
        employeesLoader.setLocation(getClass().getResource("/fxml/Employees.fxml"));
        screenController.addScreen("Employees", employeesLoader);

        FXMLLoader employeeEditLoader = new FXMLLoader();
        employeeEditLoader.setLocation(getClass().getResource("/fxml/EmployeeEdit.fxml"));
        screenController.addScreen("EmployeeEdit", employeeEditLoader);

        FXMLLoader colaLoader = new FXMLLoader();
        colaLoader.setLocation(getClass().getResource("/fxml/COLA.fxml"));
        screenController.addScreen("COLA", colaLoader);

        FXMLLoader thirteenLoader = new FXMLLoader();
        thirteenLoader.setLocation(getClass().getResource("/fxml/ThirteenPayroll.fxml"));
        screenController.addScreen("ThirteenPayroll", thirteenLoader);
    }

    public static ScreenController getScreenController() {
        return screenController;
    }
}
