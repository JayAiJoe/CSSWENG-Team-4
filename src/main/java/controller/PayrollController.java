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
            amountTc, colaTc, totalTc, sssTc, philhealthTc, pagibigTc, taxTc, lateTc, netTc, wageTc;
    @FXML
    private TableColumn<PayrollEntry, Integer> workdaysTc, timeTc, overtimeTc, deductionsTc;


    private Payroll payroll = null;
    private ObservableList<PayrollEntry> crayolaEntries = FXCollections.observableArrayList(),
            ixxiEntries = FXCollections.observableArrayList();
    private Date startDate, endDate;
    private String frequency;
    private boolean created = false;
    private boolean newPayroll = false;
    private boolean isCrayola = true;

    final String TAB_NOTSELECTED_STYLE =
            "-fx-background-color: #9CB9F0;" +
            "-fx-text-fill: white;" +
            "-fx-border-color:  #9CB9F0;" +
            "-fx-background-radius: 0px;";
    final String TAB_SELECTED_STYLE =
            " -fx-background-color: #5b62f5;" +
            " -fx-text-fill: white;" +
            " -fx-border-color:  #5b62f5;" +
            " -fx-background-radius: 0px;";
    final String TAB_HOVERED_STYLE =
            " -fx-background-color: #9CA0F1;" +
                    " -fx-text-fill: white;" +
                    " -fx-border-color:   #9CA0F1;" +
                    " -fx-background-radius: 0px;";

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        crayolaBtn.setOnMouseEntered(e -> {
            if(!isCrayola){
                crayolaBtn.setStyle(TAB_HOVERED_STYLE);
            }
        });
        crayolaBtn.setOnMouseExited(e -> {
            if(!isCrayola){
                crayolaBtn.setStyle(TAB_NOTSELECTED_STYLE);
            }
        });
        ixxiBtn.setOnMouseEntered(e -> {
            if(isCrayola){
                ixxiBtn.setStyle(TAB_HOVERED_STYLE);
            }
            });
        ixxiBtn.setOnMouseExited(e -> {
            if(isCrayola){
                ixxiBtn.setStyle(TAB_NOTSELECTED_STYLE);
            }
        });

        if (created) {
            displayPayroll.toFront();
            crayolaBtn.setStyle(TAB_SELECTED_STYLE);
            ixxiBtn.setStyle(TAB_NOTSELECTED_STYLE);
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
        daterangeText.setText("For the period:");
        crayolaBtn.setStyle(TAB_SELECTED_STYLE);
        ixxiBtn.setStyle(TAB_NOTSELECTED_STYLE);
        addressText.setText("Located at: " + "UNIT 2-3, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
        companyText.setText("Crayola atbp.");
    }

    @FXML
    public void initialize() {

        // disable row selection
        payrollTv.setSelectionModel(null);

        // initialize columns
        initPayrollCol();


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
            if(!isCrayola){
                crayolaBtn.setStyle(TAB_SELECTED_STYLE);
                ixxiBtn.setStyle(TAB_NOTSELECTED_STYLE);
                addressText.setText("Located at: " + "UNIT 2-3, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
                companyText.setText("Crayola atbp.");
                payrollTv.setItems(crayolaEntries);
                isCrayola = true;
            }
        } else if (mouseEvent.getSource() == ixxiBtn) {
            if(isCrayola){
                ixxiBtn.setStyle(TAB_SELECTED_STYLE);
                crayolaBtn.setStyle(TAB_NOTSELECTED_STYLE);
                addressText.setText("Located at: " + "UNIT 5, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
                companyText.setText("IX-XI Hardware");
                payrollTv.setItems(ixxiEntries);
                isCrayola = false;
            }

        }
    }

    /**
     * Method for returning to Home.fxml
     */
    public void onHomeClick() {
        Driver.getScreenController().activate("Home");
    }


    /**
     * Method initializes all columns
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
        initCol(wageTc, "wage", 14);
    }

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
                            new SimpleDateFormat("MM/dd/yyyy").format(startDate),
                            new SimpleDateFormat("MM/dd/yyyy").format(endDate),
                            "CRAYOLA");
                } else {
                    new ExcelHandler().printMD(filepath.getAbsolutePath(),
                            ixxiEntries,
                            new SimpleDateFormat("MM/dd/yyyy").format(startDate),
                            new SimpleDateFormat("MM/dd/yyyy").format(endDate),
                            "IX-XI Hardware");
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setGraphic(null);
                alert.setHeaderText(null);
                alert.setContentText("File printed successfully!");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setGraphic(null);
                alert.setHeaderText(null);
                alert.setContentText("Problem printing to file!");
                alert.showAndWait();
                System.out.println("Problem printing to file");
            }
        } else if(formatCb.getValue().equals("Voucher")){
            try {
                if (isCrayola) {
                    new ExcelHandler().printVoucher(filepath.getAbsolutePath(),crayolaEntries,
                            new SimpleDateFormat("MM/dd/yyyy").format(startDate),
                            new SimpleDateFormat("MM/dd/yyyy").format(endDate));
                } else {
                    new ExcelHandler().printVoucher(filepath.getAbsolutePath(),ixxiEntries,
                            new SimpleDateFormat("MM/dd/yyyy").format(startDate),
                            new SimpleDateFormat("MM/dd/yyyy").format(endDate));
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setGraphic(null);
                alert.setHeaderText(null);
                alert.setContentText("File printed successfully!");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setGraphic(null);
                alert.setHeaderText(null);
                alert.setContentText("Problem printing to file!");
                alert.showAndWait();
                System.out.println("Problem printing to file");
            }
        }

    }
}