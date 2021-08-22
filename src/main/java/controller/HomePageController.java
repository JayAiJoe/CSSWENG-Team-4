package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;

public class HomePageController extends Controller {
    @FXML
    private MenuButton menuButton;

    @Override
    public void update() {

    }

    @FXML
    public void initialize(){

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
    private void onEmployeesAction(){}

    /**
     * Changes screen to OvertimeWorkHours.fxml.
     */
    @FXML
    private void onOvertimeWorkHoursAction() {
        menuButton.hide();
        Driver.getScreenController().activate("OvertimeWorkHours");
    }

    /**
     * Changes screen to ApprovedOvertime.fxml.
     */
    @FXML
    private void onApprovedOvertimeAction(){
        menuButton.hide();
        Driver.getScreenController().activate("ApprovedOvertime");}
}
