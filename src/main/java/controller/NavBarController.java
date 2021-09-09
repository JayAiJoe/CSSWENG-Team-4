package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;

public class NavBarController{

    @FXML
    public MenuButton menuButton, employeeButton;

    final String IDLE_BUTTON_STYLE = "-fx-background-color: #577CFF; -fx-background-radius: 0px;";
    final String HOVERED_BUTTON_STYLE = "-fx-background-color: #99c9ff; -fx-background-radius: 0px;";


    public void initialize() {
        menuButton.setOnMouseEntered(e -> menuButton.setStyle(HOVERED_BUTTON_STYLE));
        menuButton.setOnMouseExited(e -> menuButton.setStyle(IDLE_BUTTON_STYLE));
        employeeButton.setOnMouseEntered(e -> employeeButton.setStyle(HOVERED_BUTTON_STYLE));
        employeeButton.setOnMouseExited(e -> employeeButton.setStyle(IDLE_BUTTON_STYLE));
    }

    /**
     * Changes screen to Home.fxml.
     */
    @FXML
    private void onHomeAction() {
        Driver.getScreenController().activate("Home");
    }

    /**
     * Changes screen to EditFees.fxml.
     */
    @FXML
    private void onEditFeesAction() {
        menuButton.hide();
        Driver.getScreenController().activate("EditFees");
    }

    /**
     * Changes screen to Payroll.fxml.
     */
    @FXML
    private void onPayrollAction() {
        Driver.getScreenController().activate("Payroll");
    }

    /**
     * Changes screen to Employees.fxml.
     */
    @FXML
    private void onEmployeesAction() {
        employeeButton.hide();
        Driver.getScreenController().activate("Employees");
    }

    @FXML
    private void onAttendanceStatisticsAction(){
        employeeButton.hide();
        Driver.getScreenController().activate("AttendanceStatistics");
    }

    /**
     * Changes screen to PendingOvertime.fxml.
     */
    @FXML
    private void onPendingOvertimeAction() {
        menuButton.hide();
        Driver.getScreenController().activate("PendingOvertime");
    }

    /**
     * Changes screen to ApprovedOvertime.fxml.
     */
    @FXML
    private void onApprovedOvertimeAction(){
        menuButton.hide();
        Driver.getScreenController().activate("ApprovedOvertimeWorkHours");
    }

    /**
     * Changes screen to COLA.fxml.
     */
    @FXML
    private void onCOLAAction(){
        employeeButton.hide();
        Driver.getScreenController().activate("COLA");
    }
}
