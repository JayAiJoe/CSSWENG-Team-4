package controller;

import dao.LogbookPOJO;
import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.AttendanceProcessor;
import model.ExcelHandler;

import java.io.File;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class HomePageController extends Controller {

    @FXML
    private AnchorPane navBar_container;

    @FXML
    private Button employeeBtn, sssBtn, philhealthBtn, pagibigBtn, attendanceBtn;
    @FXML
    private DatePicker startDatePicker, endDatePicker;
    @FXML
    private ChoiceBox<String> frequencyCb;
    @FXML
    private Text dateErrorText, frequencyErrorText;

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }
        dateErrorText.setVisible(false);
        frequencyErrorText.setVisible(false);
    }

    @FXML
    private void onCreatePayrollAction() {
        dateErrorText.setVisible(false);
        frequencyErrorText.setVisible(false);

        boolean check = true;
        Date startDate = null, endDate = null;

        try {
            startDate = Date.from(startDatePicker.getValue()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant());
            endDate = Date.from(endDatePicker.getValue()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant());

            if (startDate.compareTo(endDate) > 0) {
                check = false;
                dateErrorText.setText("Start date must be before end date!");
                dateErrorText.setVisible(true);
            }
        } catch (Exception e) {
            check = false;
            dateErrorText.setText("Dates must be filled!");
            dateErrorText.setVisible(true);
        }
        if (frequencyCb.getValue() == null) {
            check = false;
            frequencyErrorText.setText("Frequency must be filled!");
            frequencyErrorText.setVisible(true);
        }

        if (check) {
            PayrollController controller = (PayrollController) Driver
                    .getScreenController().getController("Payroll");
            controller.setPayrollInfo(startDate, endDate, frequencyCb.getValue().toUpperCase());
            Driver.getScreenController().activate("Payroll");
        }
    }

    public void onEmployeesUpload() {
        FileChooser employeefileChooser = new FileChooser();
        employeefileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File file = employeefileChooser.showOpenDialog(employeeBtn.getScene().getWindow());
        System.out.println(file);
    }

    public void onAttendanceUpload() {
        FileChooser attendancefileChooser = new FileChooser();
        attendancefileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File file = attendancefileChooser.showOpenDialog(attendanceBtn.getScene().getWindow());
        System.out.println(file);

        if (file == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.setContentText("No file selected!");
            alert.showAndWait();
            return;
        }

        try {
            // read file
            ArrayList<LogbookPOJO> logbooks = new ExcelHandler().readLogbook(file);
            // process attendance
            new AttendanceProcessor(logbooks);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.setContentText("Error reading file!");
            alert.showAndWait();
        }
    }

    public void onSSSUpload() {
        FileChooser sssfileChooser = new FileChooser();
        sssfileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File file = sssfileChooser.showOpenDialog(sssBtn.getScene().getWindow());
        System.out.println(file);
    }

    public void onPhilhealthUpload() {
        FileChooser philhealthfileChooser = new FileChooser();
        philhealthfileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File file = philhealthfileChooser.showOpenDialog(philhealthBtn.getScene().getWindow());
        System.out.println(file);
    }

    public void onPagibigUpload() {
        FileChooser pagibigfileChooser = new FileChooser();
        pagibigfileChooser.getExtensionFilters().addAll(
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