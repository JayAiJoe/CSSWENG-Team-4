package controller;

import driver.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Payroll;
import model.PayrollEntry;

/**
 * This class is the main controller for any scripted event
 * for Payroll.fxml like onClick events, listeners, etc.
 */
public class PayrollController extends Controller {


    /**
     * Instantiation of objects related to the company info in Payroll.fxml
     */
    @FXML
    private Button crayolaBtn, ixxiBtn;

    @FXML
    private Text addressText, companyText, daterangeText;

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

    Payroll payroll;
    ObservableList<PayrollEntry> entries = FXCollections.observableArrayList();

    @Override
    public void update() {
        payroll.update();
        entries.setAll(payroll.getEntries());
    }

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

        // get and set data in table
        payroll = new Payroll();
        entries.setAll(payroll.getEntries());
        payrollTv.setItems(entries);

        //disable crayolaBtn upon initialization
        crayolaBtn.setDisable(true);

        //
        addressText.setText(addressText.getText()
                + " UNIT 2-3, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
        daterangeText.setText(daterangeText.getText() + " " + payroll.getDateStart()
                + " - " + payroll.getDateEnd());

        //format column and table order
        setColumnWidth();
        disableReorder();
    }

    /**
     * This method simply disables reorderability for all table columns in Payroll.fxml
     */
    private void disableReorder() {
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
    private void setColumnWidth(){
        nameTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(8));
        modeTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(16));
        absentTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        workdaysTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        rateTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        salaryTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(11));
        overtimeTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(55));
        timeTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(25));
        amountTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        colaTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(16));
        totalTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(14));
        deductionsTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(100));
        sssTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        pagibigTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        philhealthTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        lateTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        taxTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(20));
        netTc.prefWidthProperty().bind(payrollTv.widthProperty().divide(13));
    }

    /**
     * Method responsible for switching between payroll of Crayola and IX-XI in Payroll.fxml
     * @param mouseEvent the mouse event that occurred
     */
    public void onPayrollClick(MouseEvent mouseEvent){
        if(mouseEvent.getSource() == crayolaBtn){
            crayolaBtn.setDisable(true);
            ixxiBtn.setDisable(false);
            addressText.setText("Located at: "+ "UNIT 2-3, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
            companyText.setText("Crayola atbp.");
        }
        else if (mouseEvent.getSource() == ixxiBtn) {
            ixxiBtn.setDisable(true);
            crayolaBtn.setDisable(false);
            addressText.setText("Located at: "+ "UNIT 5, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
            companyText.setText("IX-XI Hardware");

        }
    }

    /**
     * Changes screen to EditFees.fxml.
     */
    @FXML
    private void onEditFeesAction() {
        Driver.getScreenController().activate("EditFees");
    }

}