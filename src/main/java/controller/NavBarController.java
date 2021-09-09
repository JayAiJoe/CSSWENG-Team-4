package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;

public class NavBarController {

    @FXML
    public MenuButton menuButton, employeeButton;

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
