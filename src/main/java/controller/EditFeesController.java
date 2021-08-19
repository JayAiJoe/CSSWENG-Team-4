package controller;

import driver.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * This class is the main controller for any scripted event
 * for EditFees.fxml like onClick events, listeners, etc.
 */
public class EditFeesController extends Controller {
    private static DecimalFormat df = new DecimalFormat("0.00");

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
            ph_save_btn;

    @FXML
    private TextField pi_employeeTf, pi_employerTf;
    @FXML
    private Text pi_employer_errorText, pi_employee_errorText, ph_errorText;

    /**
     * Instantiation of objects related to table from EditFees.fxml
     */
    @FXML
    TableView<PhilHealthRange> philhealthTv;
    @FXML
    private TableColumn<PhilHealthRange, String> ph_start, ph_end, ph_value, ph_range;

    /**
     * Model components that are necessary for editing
     * the government fees.
     */
    PagIbigFee pagIbigFee;
    FeeTable philhealthFeeTable;
    ObservableList<PhilHealthRange> ranges;

    /**
     * This method is responsible for switching between SSS, Philhealth,
     * and Pag-ibig formula edit tabs.
     */
    public void onClickValues(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == sss_btn) {
            resetPagIbig();
            resetPhilHealth();
            System.out.println(mouseEvent.getSource().toString());
            sss_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            phil_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            pagibig_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            sss_tab.toFront();
        } else if (mouseEvent.getSource() == phil_btn) {
            resetPagIbig();
            phil_tab.toFront();
            sss_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            phil_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            pagibig_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
        } else if (mouseEvent.getSource() == pagibig_btn) {
            resetPhilHealth();
            pagibig_tab.toFront();
            sss_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            phil_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
            pagibig_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
        }
    }

    @Override
    public void update() {
        pagibig_tab.toFront();
        sss_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
        phil_btn.setStyle("-fx-background-color: #979797; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
        pagibig_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
        resetPagIbig();
        resetPhilHealth();
    }

    @FXML
    public void initialize() {
        pagibig_btn.setStyle("-fx-background-color: #616060; -fx-border-radius: 2; -fx-border-color:  #7D7D7D");
        resetPagIbig();
        resetPhilHealth();

        // PhilHealth initialization
        ph_start.setCellValueFactory(new PropertyValueFactory<>("start"));
        ph_end.setCellValueFactory(new PropertyValueFactory<>("end"));
        ph_value.setCellValueFactory(new PropertyValueFactory<>("value"));
        ph_start.setCellFactory(TextFieldTableCell.forTableColumn());
        ph_end.setCellFactory(TextFieldTableCell.forTableColumn());
        ph_value.setCellFactory(TextFieldTableCell.forTableColumn());
        ph_start.setReorderable(false);
        ph_end.setReorderable(false);
        ph_value.setReorderable(false);
        ph_range.setReorderable(false);

        // initialize editable cells
        ph_start.setOnEditStart(
                t -> {
                    int row = t.getTablePosition().getRow();
                    if (row == 0) {
                        philhealthTv.edit(-1, null);
                    }
                }
        );
        ph_end.setOnEditStart(
                t -> {
                    int row = t.getTablePosition().getRow();
                    if (row == ranges.size() - 1) {
                        philhealthTv.edit(-1, null);
                    }
                }
        );

        ph_start.setOnEditCommit(
                t -> {
                    int row = t.getTablePosition().getRow();
                    try {
                        double check = Double.parseDouble(t.getNewValue());
                        philhealthTv.getItems().get(row).setStart(df.format(check));
                    } catch (Exception e) {
                        philhealthTv.getItems().get(row).setStart(t.getNewValue());
                    }
                    if (!t.getNewValue().isEmpty()) {
                        philhealthTv.getItems().get(row).setInitialized(true);
                    }
                }
        );
        ph_end.setOnEditCommit(
                t -> {
                    int row = t.getTablePosition().getRow();
                    try {
                        double check = Double.parseDouble(t.getNewValue());
                        philhealthTv.getItems().get(row).setEnd(df.format(check));
                    } catch (Exception e) {
                        philhealthTv.getItems().get(row).setEnd(t.getNewValue());
                    }
                    if (!t.getNewValue().isEmpty()) {
                        philhealthTv.getItems().get(row).setInitialized(true);
                    }
                }
        );
        ph_value.setOnEditCommit(
                t -> {
                    int row = t.getTablePosition().getRow();
                    try {
                        double check = Double.parseDouble(t.getNewValue());
                        philhealthTv.getItems().get(row).setValue(df.format(check));
                    } catch (Exception e) {
                        philhealthTv.getItems().get(row).setValue(t.getNewValue());
                    }
                    if (!t.getNewValue().isEmpty()) {
                        philhealthTv.getItems().get(row).setInitialized(true);
                    }
                }
        );

        // get and set data
        resetRanges();
    }

    /**
     * Resets the view components in the Pag-Ibig tab.
     */
    private void resetPagIbig() {
        // Pag-Ibig initialization
        pi_edit_btn.toFront();
        pi_edit_btn.setDisable(false);
        pi_cancel_btn.toBack();
        pi_cancel_btn.setDisable(true);
        pi_cancel_btn.setVisible(false);
        pi_save_btn.setVisible(false);
        pi_save_btn.setDisable(true);
        pi_employee_errorText.setVisible(false);
        pi_employer_errorText.setVisible(false);
        pagIbigFee = Calculator.getInstance().getPagIbigFee();
        pi_employeeTf.setText(df.format(pagIbigFee.getTotalRate() * 100));
        pi_employerTf.setText(df.format(pagIbigFee.getEmployerContrib()));
    }

    /**
     * Resets the view components in the PhilHealth tab.
     */
    private void resetPhilHealth() {
        // PhilHealth initialization
        philhealthFeeTable = Calculator.getInstance().getPhilhealthFeeTable();
        philhealthTv.setEditable(false);
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
        ph_errorText.setVisible(false);
        resetRanges();
    }

    /**
     * Resets the contents of the PhilHealth fee table.
     */
    private void resetRanges() {
        ranges = FXCollections.observableArrayList();
        ArrayList<ArrayList<Double>> formulas = philhealthFeeTable.getFormulas();
        for (int i = 0; i < formulas.size(); i++) {
            double start = formulas.get(i).get(0);
            double end = formulas.get(i).get(1);
            double value = formulas.get(i).get(2);
            if (i != 0 && i != formulas.size() - 1) {
                value *= 100;
            }
            ranges.add(new PhilHealthRange(df.format(start), df.format(end), df.format(value)));
        }
        philhealthTv.setItems(ranges);
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
                pi_employee_errorText.setVisible(false);
                pi_employer_errorText.setVisible(false);

                //Textfields
                pi_employeeTf.setText(df.format(pagIbigFee.getTotalRate() * 100));
                pi_employerTf.setText(df.format(pagIbigFee.getEmployerContrib()));
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
                // check if inputs are valid
                double totalRate = 0, employerContrib = 0;
                boolean good = true;
                try {
                    totalRate = Double.parseDouble(pi_employeeTf.getText()) / 100;
                    if (totalRate <= 0 || totalRate >= 1) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    // input for employee share is invalid
                    pi_employee_errorText.setVisible(true);
                    good = false;
                }
                try {
                    employerContrib = Double.parseDouble(pi_employerTf.getText());
                    if (employerContrib < 0 || !checkDecimalPlaces(pi_employerTf.getText())) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    // input for employer contribution is invalid
                    pi_employer_errorText.setVisible(true);
                    good = false;
                }
                if (!good) { // at least one of the inputs is invalid
                    return;
                }

                // save info
                pagIbigFee.setTotalRate(totalRate);
                pagIbigFee.setEmployerContrib(employerContrib);

                pi_edit_btn.toFront();
                pi_edit_btn.setDisable(false);
                pi_cancel_btn.toBack();
                pi_cancel_btn.setDisable(true);
                pi_cancel_btn.setVisible(false);
                pi_save_btn.setVisible(false);
                pi_save_btn.setDisable(true);
                pi_employee_errorText.setVisible(false);
                pi_employer_errorText.setVisible(false);

                //Textfields
                pi_employeeTf.setDisable(true);
                pi_employerTf.setDisable(true);
                pi_employeeTf.setText(df.format(pagIbigFee.getTotalRate() * 100));
                pi_employerTf.setText(df.format(pagIbigFee.getEmployerContrib()));

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
            philhealthTv.setEditable(true);
        } else if (mouseEvent.getSource() == ph_save_btn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Apply changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // check if all inputs are positive values and up to 2 decimal places only
                ArrayList<PhilHealthRange> newRanges = new ArrayList<>();
                int rangeCount = ranges.size();
                for (int i = 0; i < rangeCount; i++) {
                    PhilHealthRange range = ranges.get(i);
                    if (!range.isInitialized()) {
                        continue;
                    }
                    try {
                        double check;
                        if (i > 0) {
                            check = Double.parseDouble(range.getStart());
                            if (check <= 0 || !checkDecimalPlaces(range.getStart())) {
                                throw new Exception();
                            }
                        }
                        if (i < rangeCount - 1) {
                            check = Double.parseDouble(range.getEnd());
                            if (check <= 0 || !checkDecimalPlaces(range.getEnd())) {
                                throw new Exception();
                            }
                        }
                        check = Double.parseDouble(range.getValue());
                        if (check <= 0 || !checkDecimalPlaces(range.getValue())) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        ph_errorText.setText("All inputs must be positive values with up to 2 decimal places only!");
                        ph_errorText.setVisible(true);
                        return;
                    }
                    newRanges.add(range);
                }
                // sort input ranges
                Collections.sort(newRanges);
                // set new ranges
                ranges.setAll(newRanges);
                // check no range is missing
                rangeCount = newRanges.size();
                for (int i = 0; i < rangeCount - 1; i++) {
                    double start = Double.parseDouble(ranges.get(i + 1).getStart());
                    double end = Double.parseDouble(ranges.get(i).getEnd());

                    if (start != end + 0.01) {
                        ph_errorText.setText("Ranges must cover all possible values for salary!");
                        ph_errorText.setVisible(true);
                        return;
                    }
                }
                for (int i = 1; i < rangeCount - 1; i++) {
                    double value = Double.parseDouble(ranges.get(i).getValue()) / 100;
                    if (value <= 0 || value >= 1) {
                        ph_errorText.setText("All percentages must be greater than 0 and less than 100!");
                    }
                }

                // update PhilHealth fee table
                ArrayList<ArrayList<Double>> formulas = new ArrayList<>();
                for (int i = 0; i < rangeCount; i++) {
                    PhilHealthRange range = ranges.get(i);
                    ArrayList<Double> newRange = range.convert();
                    if (i != 0 && i != rangeCount - 1) {
                        newRange.set(2, newRange.get(2) / 100);
                    }
                    formulas.add(newRange);
                }
                philhealthFeeTable.setFormulas(formulas);

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
                ph_errorText.setVisible(false);
                philhealthTv.setEditable(false);
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
                resetRanges();

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
                philhealthTv.setEditable(false);
                ph_errorText.setVisible(false);
                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        }
    }

    /**
     * Checks whether a given String value has up to
     * 2 decimal places only.
     * @param value the String value to be checked
     * @return true if the String has up to 2 decimals
     * only and false otherwise
     */
    private boolean checkDecimalPlaces(String value) {
        int index = value.length();
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == '.') {
                index = i;
                break;
            }
        }
        return value.length() - index <= 3;
    }

    /**
     * Adds a new row to the PhilHealth fee table.
     */
    @FXML
    private void onAddRowAction() {
        int rowCount = ranges.size();
        ranges.add(rowCount - 1, new PhilHealthRange());
    }

    /**
     * Deletes the second to the last row from the
     * PhilHealth fee table provided that there are
     * more than 2 rows in the fee table.
     */
    @FXML
    private void onDeleteRowAction() {
        int rowCount = ranges.size();
        if (rowCount > 2) {
            ranges.remove(rowCount - 2);
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
