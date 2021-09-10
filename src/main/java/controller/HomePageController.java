package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;

public class HomePageController extends Controller {

    @FXML
    private AnchorPane navBar_container;

    @FXML
    private Button employeeBtn, sssBtn, philhealthBtn, pagibigBtn, attendanceBtn;

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }
    }

    public void onEmployeesUpload(){
        FileChooser employeefileChooser = new FileChooser();
        employeefileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File file = employeefileChooser.showOpenDialog(employeeBtn.getScene().getWindow());
        System.out.println(file);
    }

    public void onAttendanceUpload(){
        FileChooser attendancefileChooser = new FileChooser();
        attendancefileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File file = attendancefileChooser.showOpenDialog(attendanceBtn.getScene().getWindow());
        System.out.println(file);
    }

    public void onSSSUpload(){
        FileChooser sssfileChooser = new FileChooser();
        sssfileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File file = sssfileChooser.showOpenDialog(sssBtn.getScene().getWindow());
        System.out.println(file);
    }

    public void onPhilhealthUpload(){
        FileChooser philhealthfileChooser = new FileChooser();
        philhealthfileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File file = philhealthfileChooser.showOpenDialog(philhealthBtn.getScene().getWindow());
        System.out.println(file);
    }

    public void onPagibigUpload(){
        FileChooser pagibigfileChooser = new FileChooser();
        pagibigfileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File file = pagibigfileChooser.showOpenDialog(pagibigBtn.getScene().getWindow());
        System.out.println(file);
    }

    public void onGoToEmployeesClick(){
        Driver.getScreenController().activate("Employees");
    }

    public void onGoToFeesClick(){
        Driver.getScreenController().activate("EditFees");
    }
}