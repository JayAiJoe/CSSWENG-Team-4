package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Optional;

public class ApprovedOvertimeController extends Controller{

    @FXML
    private AnchorPane navBar_container;

    @FXML
    private Button overtimeEditBtn, overtimeCancelBtn, overtimeSaveBtn, overtimeAddBtn, overtimeRemoveBtn;

    @FXML
    private TableColumn dateTc, startotTc, endotTc, startwh1Tc, endwh1Tc, startwh2Tc, endwh2Tc;


    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }
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
        startotTc.setReorderable(false);
        endotTc.setReorderable(false);
        startwh1Tc.setReorderable(false);
        endwh1Tc.setReorderable(false);
        startwh2Tc.setReorderable(false);
        endwh2Tc.setReorderable(false);
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


}
