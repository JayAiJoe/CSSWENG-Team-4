package controller;

import driver.Driver;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.ExcelHandler;
import model.Payroll;
import model.PayrollEntry;
import model.ThirteenPayroll;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThirteenPayrollController extends Controller {

    private static final PseudoClass COLUMN_HOVER_PSEUDO_CLASS = PseudoClass.getPseudoClass("column-hover");

    @FXML
    private AnchorPane navBar_container;

    /**
     * Instantiation of objects related to file exports in Payroll.fxml
     */
    @FXML
    private Button exportBtn;
    @FXML
    private ChoiceBox formatCb;


    /**
     * Instantiation of objects related to the company info in Payroll.fxml
     */
    @FXML
    private Button crayolaBtn, ixxiBtn, thirteenBtn;

    @FXML
    private Text addressText, companyText, daterangeText;

    /**
     * Instantiation of objects related to table from Payroll.fxml
     */
    @FXML
    private TableView<PayrollEntry> payrollTv;
    @FXML
    private TableColumn<PayrollEntry, String> nameTc, modeTc, absentTc, rateTc, salaryTc,
            amountTc, colaTc, totalTc, sssTc, philhealthTc, pagibigTc, taxTc, lateTc, netTc, wageTc;
    @FXML
    private TableColumn<PayrollEntry, Integer> workdaysTc, timeTc, overtimeTc, deductionsTc;

    private ThirteenPayroll payroll = null;
    private ObservableList<PayrollEntry> crayolaEntries = FXCollections.observableArrayList(),
            ixxiEntries = FXCollections.observableArrayList();

    private String frequency;
    private boolean isCrayola = true;

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        crayolaBtn.setDisable(true);
        ixxiBtn.setDisable(false);
        addressText.setText("Located at: " + "UNIT 2-3, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
        companyText.setText("Crayola atbp.");

        // get and set data in table
        payroll = new ThirteenPayroll(frequency);
        daterangeText.setText("13th Month");
        crayolaEntries.setAll(payroll.getCrayolaEntries());
        ixxiEntries.setAll(payroll.getIxxiEntries());
        payrollTv.setItems(crayolaEntries);


        navBar_container.toFront();
    }

    public void setPayrollInfo(String frequency) {
        this.frequency = frequency;

    }

    @FXML
    public void initialize() {

        // disable row selection
        payrollTv.setSelectionModel(null);

        // initialize columns
        initPayrollCol();

        //disable crayolaBtn upon initialization
        crayolaBtn.setDisable(true);

        addressText.setText(addressText.getText()
                + " UNIT 2-3, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
    }

    private <T> void initCol(TableColumn<PayrollEntry, T> col, String tag, int size) {
        col.setCellValueFactory(new PropertyValueFactory<>(tag));
        col.prefWidthProperty().bind(payrollTv.widthProperty().divide(size));
        col.setReorderable(false);

        // hover property
        BooleanProperty columnHover = new SimpleBooleanProperty();

        col.setCellFactory(column -> {
            TableCell<PayrollEntry, T> cell = new TableCell<>();

            if (!tag.isEmpty()) {
                cell.textProperty().bind(Bindings.createStringBinding(() -> cell.isEmpty() ? "" : String.format("%s", cell.getItem())
                        , cell.itemProperty(), cell.emptyProperty()));
            }
            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> columnHover.set(isNowHovered));

            columnHover.addListener((obs, columnWasHovered, columnIsNowHovered) ->
                    cell.pseudoClassStateChanged(COLUMN_HOVER_PSEUDO_CLASS, columnIsNowHovered)
            );
            return cell;
        });
    }

    /**
     * Method responsible for switching between payroll of Crayola and IX-XI in Payroll.fxml
     *
     * @param mouseEvent the mouse event that occurred
     */
    public void onPayrollClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == crayolaBtn) {
            crayolaBtn.setDisable(true);
            ixxiBtn.setDisable(false);
            addressText.setText("Located at: " + "UNIT 2-3, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
            companyText.setText("Crayola atbp.");
            payrollTv.setItems(crayolaEntries);
            isCrayola = true;
        } else if (mouseEvent.getSource() == ixxiBtn) {
            ixxiBtn.setDisable(true);
            crayolaBtn.setDisable(false);
            addressText.setText("Located at: " + "UNIT 5, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
            companyText.setText("IX-XI Hardware");
            payrollTv.setItems(ixxiEntries);
            isCrayola = false;
        }
    }

    /**
     * Method initializes all columns in the table
     */
    private void initPayrollCol(){
        initCol(nameTc, "employeeName", 8);
        initCol(modeTc, "mode", 18);
        initCol(absentTc, "absent", 20);
        initCol(workdaysTc, "workdays", 20);
        initCol(rateTc, "rate", 20);
        initCol(salaryTc, "salary", 16);
        initCol(overtimeTc, "overtime", 55);
        initCol(timeTc, "time", 25);
        initCol(amountTc, "amount", 20);
        initCol(colaTc, "cola", 18);
        initCol(totalTc, "total", 16);
        initCol(deductionsTc, "deductions", 100);
        initCol(sssTc, "sss", 20);
        initCol(philhealthTc, "philhealth", 20);
        initCol(pagibigTc, "pagibig", 20);
        initCol(lateTc, "late", 20);
        initCol(taxTc, "", 20);
        initCol(netTc, "net", 16);
        initCol(wageTc, "wage", 16);
    }

    /**
     * Method for exporting 13th Month payroll
     */
    public void onExportClick() {
        FileChooser exportfileChooser = new FileChooser();
        exportfileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File filepath = exportfileChooser.showSaveDialog(exportBtn.getScene().getWindow());
        System.out.println(filepath);
        if (formatCb.getValue().equals("Standard")) {
            try {
                if (isCrayola) {
                    new ExcelHandler().printMD(filepath.getAbsolutePath(),
                            crayolaEntries,
                            "13th month","",
                            "Crayola atbp.");
                } else {
                    new ExcelHandler().printMD(filepath.getAbsolutePath(),
                            ixxiEntries,
                            "13th month",
                            "",
                            "IX-XI Hardware");
                }
            } catch (Exception e) {
                System.out.println("Problem printing to file");
            }
        } else if(formatCb.getValue().equals("Voucher")){
            try {
                if (isCrayola) {
                    new ExcelHandler().printVoucher(filepath.getAbsolutePath(),crayolaEntries,
                            "13th month",
                            "");
                } else {
                    new ExcelHandler().printVoucher(filepath.getAbsolutePath(),ixxiEntries,
                            "13th month",
                            "");
                }
            } catch (Exception e) {
                System.out.println("Problem printing to file");
            }
        }

    }

    public void onHomeClick(){
        Driver.getScreenController().activate("Home");
    }
}
