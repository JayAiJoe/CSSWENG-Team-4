package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Payroll;
import model.PayrollEntry;

/**
 * This class is the main controller for any scripted event
 * for Payroll.fxml like onClick events, listeners, etc.
 */
public class PayrollController {

    /** Instantiation of the different tabs in the calculator*/
    @FXML
    private Pane sss_tab, phil_tab, pagibig_tab;

    /** Instantiation of Button objects from Payroll.fxml*/
    @FXML
    private Button sss_btn, phil_btn, pagibig_btn, edit_btn, cancel_btn, add_btn, delete_btn, save_btn;

    /** Instantiation of objects related to table from Payroll.fxml*/
    @FXML
    private TableView<PayrollEntry> payrollTv;
    @FXML
    private TableColumn<PayrollEntry, String> nameTc, modeTc;
    @FXML
    private TableColumn<PayrollEntry, Integer> workdaysTc, timeTc;
    @FXML
    private TableColumn<PayrollEntry, Double> rateTc, salaryTc, amountTc, colaTc, totalTc, sssTc,
                                                philhealthTc, pagibigTc, lateTc;

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

    @FXML
    public void initialize() {
        // disable row selection
        payrollTv.setSelectionModel(null);

        // initialize columns
        nameTc.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        modeTc.setCellValueFactory(new PropertyValueFactory<>("mode"));
        workdaysTc.setCellValueFactory(new PropertyValueFactory<>("workdays"));
        timeTc.setCellValueFactory(new PropertyValueFactory<>("time"));
        rateTc.setCellValueFactory(new PropertyValueFactory<>("rate"));
        salaryTc.setCellValueFactory(new PropertyValueFactory<>("salary"));
        amountTc.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colaTc.setCellValueFactory(new PropertyValueFactory<>("cola"));
        totalTc.setCellValueFactory(new PropertyValueFactory<>("total"));
        sssTc.setCellValueFactory(new PropertyValueFactory<>("sss"));
        philhealthTc.setCellValueFactory(new PropertyValueFactory<>("philhealth"));
        pagibigTc.setCellValueFactory(new PropertyValueFactory<>("pagibig"));
        lateTc.setCellValueFactory(new PropertyValueFactory<>("late"));

        // get data
        Payroll payroll = new Payroll();
        ObservableList<PayrollEntry> entries = FXCollections.observableArrayList();
        entries.setAll(payroll.getEntries());

        // set data in table
        payrollTv.setItems(entries);
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
