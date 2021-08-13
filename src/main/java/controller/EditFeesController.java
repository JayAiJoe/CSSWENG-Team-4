package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.PayrollEntry;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * This class is the main controller for any scripted event
 * for EditFees.fxml like onClick events, listeners, etc.
 */
public class EditFeesController {

    /**
     * Instantiation of the different tabs in the calculator
     */
    @FXML
    private Pane sss_tab, phil_tab, pagibig_tab;

    /**
     * Instantiation of Button objects from EditFees.fxml
     */
    @FXML
    private Button sss_btn, phil_btn, pagibig_btn, pi_edit_btn, pi_cancel_btn,
            pi_save_btn, ph_update_btn, ph_cancel_btn, ph_add_btn, ph_delete_btn,
            ph_save_btn, Payroll_nav;

    @FXML
    private TextField pi_maxTf, pi_employeeTf, pi_employerTf;

    /**
     * Instantiation of objects related to table from EditFees.fxml
     */
    @FXML
    private TableColumn<PayrollEntry, Double> ph_range, ph_start, ph_end, ph_rate;

    /**
     * This method is responsible for switching between SSS, Philhealth,
     * and Pag-ibig formula edit tabs.
     */
    public void onClickValues(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == sss_btn) {
            System.out.println(mouseEvent.getSource().toString());
            sss_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            phil_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            pagibig_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            sss_tab.toFront();
        } else if (mouseEvent.getSource() == phil_btn) {
            phil_tab.toFront();
            sss_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            phil_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            pagibig_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
        } else if (mouseEvent.getSource() == pagibig_btn) {
            pagibig_tab.toFront();
            sss_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            phil_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            pagibig_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
        }
    }

    @FXML
    public void initialize() {
        // highlight pagibig_btn
        pagibig_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");

        disableReorder();
    }

    /**
     * This method simply disables reordability for all table columns in EditFees.fxml.
     */
    public void disableReorder() {
        // philhealth
        ph_range.setReorderable(false);
        ph_start.setReorderable(false);
        ph_end.setReorderable(false);
        ph_rate.setReorderable(false);
    }

    /**
     * This method is responsible for handling the Pag-ibig tab.
     */
    public void onPIEditClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == pi_edit_btn) {
            //buttons
            pi_edit_btn.toBack();
            pi_edit_btn.setDisable(true);
            pi_cancel_btn.toFront();
            pi_cancel_btn.setDisable(false);
            pi_cancel_btn.setVisible(true);
            pi_save_btn.setVisible(true);
            pi_save_btn.setDisable(false);

            //Textfields
            pi_maxTf.setDisable(false);
            pi_employeeTf.setDisable(false);
            pi_employerTf.setDisable(false);
        } else if (mouseEvent.getSource() == pi_cancel_btn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Cancel changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //buttons
                pi_edit_btn.toFront();
                pi_edit_btn.setDisable(false);
                pi_cancel_btn.toBack();
                pi_cancel_btn.setDisable(true);
                pi_cancel_btn.setVisible(false);
                pi_save_btn.setVisible(false);
                pi_save_btn.setDisable(true);

                //Textfields
                pi_maxTf.setDisable(true);
                pi_employeeTf.setDisable(true);
                pi_employerTf.setDisable(true);
                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }

        } else if (mouseEvent.getSource() == pi_save_btn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Apply changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                pi_edit_btn.toFront();
                pi_edit_btn.setDisable(false);
                pi_cancel_btn.toBack();
                pi_cancel_btn.setDisable(true);
                pi_cancel_btn.setVisible(false);
                pi_save_btn.setVisible(false);
                pi_save_btn.setDisable(true);

                //Textfields
                pi_maxTf.setDisable(true);
                pi_employeeTf.setDisable(true);
                pi_employerTf.setDisable(true);

                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        }
    }

    /**
     * Method responsible for button functions in the Philhealth tab for calculator
     */
    public void onPHEditClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == ph_update_btn) {
            ph_update_btn.toBack();
            ph_update_btn.setDisable(true);
            ph_cancel_btn.toFront();
            ph_cancel_btn.setDisable(false);
            ph_cancel_btn.setVisible(true);
            ph_add_btn.setVisible(true);
            ph_add_btn.setDisable(false);
            ph_delete_btn.setVisible(true);
            ph_delete_btn.setDisable(false);
            ph_save_btn.setVisible(true);
            ph_save_btn.setDisable(false);
        } else if (mouseEvent.getSource() == ph_save_btn) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Apply changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ph_update_btn.toFront();
                ph_update_btn.setDisable(false);
                ph_cancel_btn.toBack();
                ph_cancel_btn.setDisable(true);
                ph_cancel_btn.setVisible(false);
                ph_add_btn.setVisible(false);
                ph_add_btn.setDisable(true);
                ph_delete_btn.setVisible(false);
                ph_delete_btn.setDisable(true);
                ph_save_btn.setVisible(false);
                ph_save_btn.setDisable(true);
                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        } else if (mouseEvent.getSource() == ph_cancel_btn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Discard changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ph_update_btn.toFront();
                ph_update_btn.setDisable(false);
                ph_cancel_btn.toBack();
                ph_cancel_btn.setDisable(true);
                ph_cancel_btn.setVisible(false);
                ph_add_btn.setVisible(false);
                ph_add_btn.setDisable(true);
                ph_delete_btn.setVisible(false);
                ph_delete_btn.setDisable(true);
                ph_save_btn.setVisible(false);
                ph_save_btn.setDisable(true);
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
}
