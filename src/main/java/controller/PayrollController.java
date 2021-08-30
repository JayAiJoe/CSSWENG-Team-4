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
import model.Payroll;
import model.PayrollEntry;

/**
 * This class is the main controller for any scripted event
 * for Payroll.fxml like onClick events, listeners, etc.
 */
public class PayrollController extends Controller {
    private static final PseudoClass COLUMN_HOVER_PSEUDO_CLASS = PseudoClass.getPseudoClass("column-hover");

    @FXML
    private AnchorPane navBar_container;

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
        if (payroll == null) {
            payroll = new Payroll();
            daterangeText.setText(daterangeText.getText() + " " + payroll.getDateStart()
                    + " - " + payroll.getDateEnd());
        } else {
            payroll.update();
        }
        crayolaEntries.setAll(payroll.getCrayolaEntries());
        ixxiEntries.setAll(payroll.getIxxiEntries());
        payrollTv.setItems(crayolaEntries);
    }

    @FXML
    public void initialize() {

        // disable row selection
        payrollTv.setSelectionModel(null);

        // initialize columns
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
        } else if (mouseEvent.getSource() == ixxiBtn) {
            ixxiBtn.setDisable(true);
            crayolaBtn.setDisable(false);
            addressText.setText("Located at: " + "UNIT 5, U&I BLDG., F. TANEDO ST., SAN NICOLAS BLK 8, TARLAC CITY");
            companyText.setText("IX-XI Hardware");
            payrollTv.setItems(ixxiEntries);
        }
    }
}