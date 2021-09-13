package controller;

import dao.EmployeePOJO;
import dao.LogbookPOJO;
import dao.PayrollPOJO;
import dao.Repository;
import driver.Driver;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.AttendanceProcessor;
import model.ExcelHandler;
import wrapper.PayrollWrapper;

import java.io.File;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class HomePageController extends Controller {

    @FXML
    private AnchorPane navBar_container;

    @FXML
    private Button employeeBtn, sssBtn, attendanceBtn;
    @FXML
    private DatePicker startDatePicker, endDatePicker;
    @FXML
    private ChoiceBox<String> frequencyCb, frequencyCb2;
    @FXML
    private Text dateErrorText, frequencyErrorText, frequencyErrorText2;
    @FXML
    private TableView<PayrollWrapper> payrollListTv;
    @FXML
    private TableColumn<PayrollWrapper, String> startTc, endTc, frequencyTc, rangeTc, buttonTc;

    private Date startDate, endDate;
    private String frequency;

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }
        dateErrorText.setVisible(false);
        frequencyErrorText.setVisible(false);
        frequencyErrorText2.setVisible(false);

        ArrayList<PayrollWrapper> entries = new ArrayList<>();
        for (PayrollPOJO payroll: Repository.getInstance().getAllPayrolls()) {
            entries.add(new PayrollWrapper(payroll));
        }
        payrollListTv.setItems(FXCollections.observableList(entries));
    }

    @FXML
    public void initialize() {
        disableReorder();

        startTc.setCellValueFactory(new PropertyValueFactory<>("startDateString"));
        endTc.setCellValueFactory(new PropertyValueFactory<>("endDateString"));
        frequencyTc.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        buttonTc.setCellFactory(t -> {
            Button button = new Button();
            button.setText("View");
            TableCell<PayrollWrapper, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            };

            button.setOnMouseClicked(e -> {
                if (cell.getTableRow().getItem() != null) {
                    PayrollWrapper payroll = cell.getTableRow().getItem();
                    PayrollController controller = (PayrollController) Driver
                            .getScreenController().getController("Payroll");
                    controller.setPayrollInfo(payroll.getStartDate(), payroll.getEndDate(),
                            payroll.getFrequency());
                    Driver.getScreenController().activate("Payroll");
                }
            });

            return cell;
        });
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

    @FXML
    private void onCreateThirteenAction(){
        frequencyErrorText2.setVisible(false);
        boolean check = true;
        if (frequencyCb2.getValue() == null) {
            check = false;
            frequencyErrorText2.setText("Frequency must be filled!");
            frequencyErrorText2.setVisible(true);
        }

        if(check){
            ThirteenPayrollController controller = (ThirteenPayrollController) Driver
                    .getScreenController().getController("ThirteenPayroll");
            controller.setPayrollInfo(frequencyCb2.getValue().toUpperCase());

            Driver.getScreenController().activate("ThirteenPayroll");
        }

    }

    public void onEmployeesUpload() {
        FileChooser employeefileChooser = new FileChooser();
        employeefileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File file = employeefileChooser.showOpenDialog(employeeBtn.getScene().getWindow());
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
            ArrayList<EmployeePOJO> employees = new ExcelHandler().readEmployees(file);
            for (EmployeePOJO employee: employees) {
                Repository.getInstance().addEmployee(employee);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.setContentText("Error reading file!");
            alert.showAndWait();
        }
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.setContentText("Attendance processed successfully!");
            alert.showAndWait();
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

    public void onGoToEmployeesClick() {
        Driver.getScreenController().activate("Employees");
    }

    public void onGoToFeesClick() {
        Driver.getScreenController().activate("EditFees");
    }

    private void disableReorder(){
        endTc.setReorderable(false);
        startTc.setReorderable(false);
        rangeTc.setReorderable(false);
        buttonTc.setReorderable(false);
        frequencyTc.setReorderable(false);
    }

}