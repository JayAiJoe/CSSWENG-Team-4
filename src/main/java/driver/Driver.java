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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/NavBar.fxml")));
        Scene scene = new Scene(root);
        initScreenController(scene);
        screenController.activate("Home");

        try {
            Repository.getInstance();
        } catch (Exception e) {
            // TODO: Error establishing connection to db
            System.out.println("Error establishing connection to the database!");
        }

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
        pendingOvertimeLoader.setLocation(getClass().getResource("/fxml/PendingOvertime.fxml"));
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
    }

    public static ScreenController getScreenController() {
        return screenController;
    }
}
