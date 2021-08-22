package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class ApprovedOvertimeController extends Controller{

    @FXML
    private MenuButton menuButton;

    @FXML
    private Button overtimeEditBtn, overtimeCancelBtn, overtimeSaveBtn, overtimeAddBtn, overtimeRemoveBtn;

    @FXML
    private TableColumn dateTc, startTc, endTc;


    @Override
    public void update() {

    }

    @FXML
    public void initialize(){

        disableReorder();
    }

    /**
     * Method for adding a table row
     * @param mouseEvent
     */
    public void onAddClick (MouseEvent mouseEvent){

    }

    /**
     * Method for removing a table row
     * @param mouseEvent
     */
    public void onRemoveClick (MouseEvent mouseEvent){

    }

    /**
     * This method simply disables reorderability for all table columns in Payroll.fxml
     */
    private void disableReorder() {
        startTc.setReorderable(false);
        endTc.setReorderable(false);
        dateTc.setReorderable(false);
    }

    /**
     * This method is responsible for handling the edits for approved overtime hours.
     */
    public void onOvertimeEditClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == overtimeEditBtn) {
            //buttons
            overtimeEditBtn.setVisible(false);
            overtimeEditBtn.setDisable(true);
            overtimeCancelBtn.setDisable(false);
            overtimeCancelBtn.setVisible(true);
            overtimeSaveBtn.setVisible(true);
            overtimeSaveBtn.setDisable(false);

        } else if (mouseEvent.getSource() == overtimeCancelBtn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Cancel changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //buttons
                overtimeEditBtn.setVisible(true);
                overtimeEditBtn.setDisable(false);
                overtimeCancelBtn.setVisible(false);
                overtimeCancelBtn.setDisable(true);
                overtimeSaveBtn.setVisible(false);
                overtimeSaveBtn.setDisable(true);
                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        } else if (mouseEvent.getSource() == overtimeSaveBtn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Apply changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                overtimeEditBtn.setVisible(true);
                overtimeEditBtn.setDisable(false);
                overtimeCancelBtn.setVisible(false);
                overtimeCancelBtn.setDisable(true);
                overtimeSaveBtn.setVisible(false);
                overtimeSaveBtn.setDisable(true);

                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        }
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
     * Changes screen to Home.fxml.
     */
    @FXML
    private void onHomeAction() {
        Driver.getScreenController().activate("Home");
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
    private void onOvertimeWorkHoursAction(){
        menuButton.hide();
        Driver.getScreenController().activate("OvertimeWorkHours");
    }


}
