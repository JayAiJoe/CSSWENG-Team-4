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
    private TableColumn<PayrollEntry, String> nameTc, modeTc, absentTc, rateTc, salaryTc,
            amountTc, colaTc, totalTc, sssTc, philhealthTc, pagibigTc, taxTc, lateTc, netTc;
    @FXML
    private TableColumn<PayrollEntry, Integer> workdaysTc, timeTc, overtimeTc, deductionsTc;

    @FXML
    public void initialize() {
        // disable row selection
        payrollTv.setSelectionModel(null);

        // initialize columns
        nameTc.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        modeTc.setCellValueFactory(new PropertyValueFactory<>("mode"));
        absentTc.setCellValueFactory(new PropertyValueFactory<>("absent"));
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

        //format column and table order
        setColumnWidth();
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
        deductionsTc.setReorderable(false);
        lateTc.setReorderable(false);
        modeTc.setReorderable(false);
        nameTc.setReorderable(false);
        netTc.setReorderable(false);
        overtimeTc.setReorderable(false);
        pagibigTc.setReorderable(false);
        philhealthTc.setReorderable(false);
        rateTc.setReorderable(false);
        salaryTc.setReorderable(false);
        sssTc.setReorderable(false);
        taxTc.setReorderable(false);
        workdaysTc.setReorderable(false);
    }

    /**
     * Method sets the width of each Table Column in payrollTv
     * */
    public void setColumnWidth(){
        nameTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(8));
        modeTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(16));
        absentTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        workdaysTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        rateTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        salaryTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(11));
        overtimeTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(16));
        timeTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(25));
        amountTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        colaTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(16));
        totalTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(14));
        deductionsTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(11));
        sssTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        pagibigTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        philhealthTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        lateTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        taxTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        netTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(13));
    }

    /**
     * Changes screen to EditFees.fxml.
     */
    @FXML
    private void onEditFeesAction() {
        Driver.getScreenController().activate("EditFees");
    }
}