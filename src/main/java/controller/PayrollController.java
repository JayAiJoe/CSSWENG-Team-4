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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is the main controller for any scripted event
 * for Payroll.fxml like onClick events, listeners, etc.
 */
public class PayrollController extends Controller {
    private static final PseudoClass COLUMN_HOVER_PSEUDO_CLASS = PseudoClass.getPseudoClass("column-hover");

    @FXML
    private AnchorPane navBar_container, emptyPayroll, displayPayroll;

    /**
     * Instantiation of objects related to the company info in Payroll.fxml
     */
    @FXML
    private Button crayolaBtn, ixxiBtn, thirteenBtn, exportBtn;

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
    private TableColumn<PayrollEntry, Integer> workdaysTc, timeTc, overtimeTc, deductionsTc, thirteenTc;

    @FXML
    private CheckBox toggleThirteen;

    private Payroll payroll = null;
    private ObservableList<PayrollEntry> crayolaEntries = FXCollections.observableArrayList(),
            ixxiEntries = FXCollections.observableArrayList();
    private Date startDate, endDate;
    private String frequency;
    private boolean created = false;
    private boolean newPayroll = false;
    private boolean isCrayola = true;

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        if (created) {
            displayPayroll.toFront();

            crayolaBtn.setDisable(true);
            ixxiBtn.setDisable(false);
            addressText.setText("Located at: " + "UNIT 2-3, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
            companyText.setText("Crayola atbp.");

            if (newPayroll) {
                // get and set data in table
                payroll = new Payroll(startDate, endDate, frequency);
                daterangeText.setText(daterangeText.getText() + " " +
                        new SimpleDateFormat("MM/dd/yyyy").format(startDate) + " - " +
                        new SimpleDateFormat("MM/dd/yyyy").format(endDate));
                crayolaEntries.setAll(payroll.getCrayolaEntries());
                ixxiEntries.setAll(payroll.getIxxiEntries());
                payrollTv.setItems(crayolaEntries);
                newPayroll = false;
            }
        } else {
            emptyPayroll.toFront();
        }
        navBar_container.toFront();
    }

    public void setPayrollInfo(Date startDate, Date endDate, String frequency) {
        if(created){
            resetPayroll();
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
        this.created = true;
        this.newPayroll = true;
    }
    
    public void resetPayroll(){
        created = false;
        newPayroll = false;
        initPayrollCol();
        thirteenBtn.setVisible(false);
        thirteenTc.setVisible(false);
        toggleThirteen.setSelected(false);
        daterangeText.setText("For the period:");
        crayolaBtn.setDisable(true);
        ixxiBtn.setDisable(false);
        addressText.setText("Located at: " + "UNIT 2-3, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
        companyText.setText("Crayola atbp.");
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
     * Method for returning to Home.fxml
     */
    public void onHomeClick() {
        Driver.getScreenController().activate("Home");
    }


    /**
     * Method for adding or removing 13th Month column in the table
     */
    public void onThirteenClick() {
        if (toggleThirteen.isSelected()) {
            initCol(thirteenTc, "13th Month", 16);
            init13thCol();
            thirteenBtn.setVisible(true);
            thirteenTc.setVisible(true);
        }
        else {
            initPayrollCol();
            thirteenBtn.setVisible(false);
            thirteenTc.setVisible(false);
        }
    }

    /**
     * Method initializes all columns with 13th Month Pay not being present in the table
     */
    private void initPayrollCol() {
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
     * Method Initializes all Columns with 13th Month pay included in the table
     */
    private void init13thCol() {
        initCol(nameTc, "employeeName", 9);
        initCol(modeTc, "mode", 19);
        initCol(absentTc, "absent", 21);
        initCol(workdaysTc, "workdays", 21);
        initCol(rateTc, "rate", 21);
        initCol(salaryTc, "salary", 17);
        initCol(overtimeTc, "overtime", 57);
        initCol(timeTc, "time", 26);
        initCol(amountTc, "amount", 21);
        initCol(colaTc, "cola", 19);
        initCol(totalTc, "total", 17);
        initCol(deductionsTc, "deductions", 105);
        initCol(sssTc, "sss", 21);
        initCol(philhealthTc, "philhealth", 21);
        initCol(pagibigTc, "pagibig", 21);
        initCol(lateTc, "late", 21);
        initCol(taxTc, "", 21);
        initCol(netTc, "net", 17);
        initCol(wageTc, "wage", 17);
    }

    public void onViewThirteenClick() {
        Driver.getScreenController().activate("ThirteenPayroll");
    }

    public void onExportClick() {
        FileChooser exportfileChooser = new FileChooser();
        exportfileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"));
        File filepath = exportfileChooser.showSaveDialog(exportBtn.getScene().getWindow());
        System.out.println(filepath);
        //TODO: Save payroll to excel using filepath
        try {
            if (isCrayola) {
                new ExcelHandler().printMD(filepath.getAbsolutePath(),
                        crayolaEntries,
                        new SimpleDateFormat("MM/dd/yyyy").format(startDate),
                        new SimpleDateFormat("MM/dd/yyyy").format(endDate),
                        "Crayola atbp.");
            } else {
                new ExcelHandler().printMD(filepath.getAbsolutePath(),
                        ixxiEntries,
                        new SimpleDateFormat("MM/dd/yyyy").format(startDate),
                        new SimpleDateFormat("MM/dd/yyyy").format(endDate),
                        "IX-XI Hardware");
            }
        } catch (Exception e) {
            System.out.println("Problem printing to file");
        }
    }
}