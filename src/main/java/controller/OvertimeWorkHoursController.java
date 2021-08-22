package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.PayrollEntry;

import java.util.Optional;

public class OvertimeWorkHoursController extends Controller{
    @FXML
    private MenuButton menuButton;

    /**
     * initialization of Overtime Table related objects
     */
    @FXML
    private TableColumn nameTc, overtimeTc, dateTc, buttonTc;

    @FXML
    private TableView overtimeTv;

    /**
     * initialization of work hours and overtime related objects
     */
    @FXML
    private Button workEditBtn, workCancelBtn, workSaveBtn, overtimeEditBtn, overtimeCancelBtn, overtimeSaveBtn,
            acceptBtn, rejectBtn, checkAllBtn;
    @FXML
    private TextField overtimeStartTf, overtimeEndTf, workStartTf, workEndTf, workStartTf2, workEndTf2;

    @Override
    public void update() {

    }

    @FXML
    public void initialize(){

        //setColumnWidth();
        disableReorder();
    }

    /**
     * This method sets the size of table columns present in OvertimeWorkHours.fxml
     */

    private void setColumnWidth(){
        nameTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        overtimeTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        dateTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        buttonTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
    }



    /**
     * This method simply disables reorderability for all table columns in Payroll.fxml
     */
    private void disableReorder() {
        nameTc.setReorderable(false);
        overtimeTc.setReorderable(false);
        dateTc.setReorderable(false);
        buttonTc.setReorderable(false);
    }

    /**
     * This method is responsible for handling the edits for work hours.
     */
    public void onWorkEditClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == workEditBtn) {
            //buttons
            workEditBtn.setVisible(false);
            workEditBtn.setDisable(true);
            workCancelBtn.setDisable(false);
            workCancelBtn.setVisible(true);
            workSaveBtn.setVisible(true);
            workSaveBtn.setDisable(false);

            //Textfields
            workStartTf.setDisable(false);
            workEndTf.setDisable(false);
            workStartTf2.setDisable(false);
            workEndTf2.setDisable(false);
        } else if (mouseEvent.getSource() == workCancelBtn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Cancel changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //buttons
                workEditBtn.setVisible(true);
                workEditBtn.setDisable(false);
                workCancelBtn.setVisible(false);
                workCancelBtn.setDisable(true);
                workSaveBtn.setVisible(false);
                workSaveBtn.setDisable(true);

                //Textfields
                workStartTf.setDisable(true);
                workEndTf.setDisable(true);
                workStartTf2.setDisable(true);
                workEndTf2.setDisable(true);

                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        } else if (mouseEvent.getSource() == workSaveBtn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Apply changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                workEditBtn.setVisible(true);
                workEditBtn.setDisable(false);
                workCancelBtn.setVisible(false);
                workCancelBtn.setDisable(true);
                workSaveBtn.setVisible(false);
                workSaveBtn.setDisable(true);

                //Textfields
                workStartTf.setDisable(true);
                workEndTf.setDisable(true);
                workStartTf2.setDisable(true);
                workEndTf2.setDisable(true);

                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        }
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

            //Textfields
            overtimeStartTf.setDisable(false);
            overtimeEndTf.setDisable(false);
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

                //Textfields
                overtimeStartTf.setDisable(true);
                overtimeEndTf.setDisable(true);

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

                //Textfields
                overtimeStartTf.setDisable(true);
                overtimeEndTf.setDisable(true);

                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        }
    }


    /**
     * Changes screen to Payroll.fxml.
     */
    @FXML
    private void onPayrollAction() {
        Driver.getScreenController().activate("Payroll");
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

}
