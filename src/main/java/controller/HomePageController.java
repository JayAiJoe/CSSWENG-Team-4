package controller;

import driver.Driver;
import javafx.fxml.FXML;

public class HomePageController extends Controller {



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
    private void onOvertimeWorkHoursAction(){Driver.getScreenController().activate("OvertimeWorkHours");}
}
