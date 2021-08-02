package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the main controller for any scripted event
 * for Payroll.fxml like onClick events, listeners, etc.
 */
public class PayrollController implements Initializable {

    /** Instantiation of the different tabs in the calculator*/
    @FXML
    private Pane sss_tab, phil_tab, pagibig_tab;

    /** Instantiation of Button objects from Payroll.fxml*/
    @FXML
    private Button sss_btn, phil_btn, pagibig_btn, edit_btn, cancel_btn, add_btn, delete_btn, save_btn;

    /** Method responsible for switching between SSS, Philhealth,
     * and Pag-ibig formula edit tabs
     */
    public void onClickValues(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == sss_btn){
            System.out.println(mouseEvent.getSource().toString());
            sss_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            phil_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            pagibig_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            sss_tab.toFront();
        }
        else if(mouseEvent.getSource() == phil_btn){
            phil_tab.toFront();
            sss_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            phil_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            pagibig_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
        }
        else if(mouseEvent.getSource() == pagibig_btn){
            pagibig_tab.toFront();
            sss_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            phil_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            pagibig_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void onEditClick(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == edit_btn){
            edit_btn.toBack();
            edit_btn.setDisable(true);
            cancel_btn.toFront();
            cancel_btn.setDisable(false);
            add_btn.setVisible(true);
            add_btn.setDisable(false);
            delete_btn.setVisible(true);
            delete_btn.setDisable(false);
            save_btn.setVisible(true);
            save_btn.setDisable(false);

        }
        else if(mouseEvent.getSource() == cancel_btn){
            edit_btn.toFront();
            edit_btn.setDisable(false);
            cancel_btn.toBack();
            cancel_btn.setDisable(true);
            add_btn.setVisible(false);
            add_btn.setDisable(true);
            delete_btn.setVisible(false);
            delete_btn.setDisable(true);
            save_btn.setVisible(false);
            save_btn.setDisable(true);
        }
    }
}
