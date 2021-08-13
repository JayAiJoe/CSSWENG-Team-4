package controller;

import driver.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Payroll;
import model.PayrollEntry;

/**
 * This class is the main controller for any scripted event
 * for Payroll.fxml like onClick events, listeners, etc.
 */
public class PayrollController {
    /**
     * Instantiation of objects related to table from Payroll.fxml
     */
    @FXML
    private TableView<PayrollEntry> payrollTv;
    @FXML
    private TableColumn<PayrollEntry, String> nameTc, modeTc, rateTc, salaryTc,
            amountTc, colaTc, totalTc, sssTc, philhealthTc, pagibigTc, lateTc, netTc;
    @FXML
    private TableColumn<PayrollEntry, Integer> workdaysTc, timeTc;

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
        netTc.setCellValueFactory(new PropertyValueFactory<>("net"));

        // get data
        Payroll payroll = new Payroll();
        ObservableList<PayrollEntry> entries = FXCollections.observableArrayList();
        entries.setAll(payroll.getEntries());

        // set data in table
        payrollTv.setItems(entries);
        disableReorder();
    }

    /**
     * This method simply disables reorderability for all table columns in Payroll.fxml
     */
    public void disableReorder() {
        //Payroll table
        timeTc.setReorderable(false);
        totalTc.setReorderable(false);
        amountTc.setReorderable(false);
        colaTc.setReorderable(false);
        lateTc.setReorderable(false);
        modeTc.setReorderable(false);
        nameTc.setReorderable(false);
        netTc.setReorderable(false);
        pagibigTc.setReorderable(false);
        philhealthTc.setReorderable(false);
        rateTc.setReorderable(false);
        salaryTc.setReorderable(false);
        sssTc.setReorderable(false);
        workdaysTc.setReorderable(false);
    }

    /**
     * Changes screen to EditFees.fxml.
     */
    @FXML
    private void onEditFeesAction() {
        Driver.getScreenController().activate("EditFees");
    }
}