package controller;

import driver.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.*;

import java.io.IOException;
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
    @FXML
    private AnchorPane navBar_container;

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
    private PagIbigFee pagIbigFee;
    private FeeTable philhealthFeeTable;
    private ObservableList<PhilHealthRange> ranges;
    private int editRow;

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

        //load navigation bar
        FXMLLoader loader = new FXMLLoader();
        try {
            Node node  =  loader.load(getClass().getResource("/fxml/navBar.fxml").openStream());
            navBar_container.getChildren().add(node);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

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

        // remove symbols on edit start
        ph_start.setOnEditStart(
                t -> {
                    int row = t.getTablePosition().getRow();
                    editRow = row;
                    if (row == 0) {
                        philhealthTv.edit(-1, null);
                    } else {
                        String start = ranges.get(row).getStart();
                        if (!start.isEmpty() && start.charAt(0) == 'P') {
                            ranges.get(row).setStart(start.substring(4));
                            philhealthTv.refresh();
                        }
                        ph_errorText.setVisible(false);
                    }
                }
        );
        ph_end.setOnEditStart(
                t -> {
                    int row = t.getTablePosition().getRow();
                    editRow = row;
                    if (row == ranges.size() - 1) {
                        philhealthTv.edit(-1, null);
                    } else {
                        String end = ranges.get(row).getEnd();
                        if (!end.isEmpty() && end.charAt(0) == 'P') {
                            ranges.get(row).setEnd(end.substring(4));
                            philhealthTv.refresh();
                        }
                        ph_errorText.setVisible(false);
                    }
                }
        );
        ph_value.setOnEditStart(
                t -> {
                    int row = t.getTablePosition().getRow();
                    editRow = row;
                    String value = ranges.get(row).getValue();
                    if (row == 0 || row == ranges.size() - 1) {
                        if (!value.isEmpty() && value.charAt(0) == 'P') {
                            ranges.get(row).setValue(value.substring(4));
                            philhealthTv.refresh();
                        }
                    } else {
                        if (!value.isEmpty() && value.charAt(value.length() - 1) == '%') {
                            ranges.get(row).setValue(value.substring(0, value.length() - 2));
                            philhealthTv.refresh();
                        }
                    }
                    ph_errorText.setVisible(false);
                }
        );

        // add symbols back on edit cancel
        ph_start.setOnEditCancel(
                t -> {
                    int row = editRow;
                    if (row != 0) {
                        String start = ranges.get(row).getStart();
                        if (!start.isEmpty() && start.charAt(0) != 'P') {
                            ranges.get(row).setStart("PhP " + start);
                            philhealthTv.refresh();
                        }
                    }
                }
        );
        ph_end.setOnEditCancel(
                t -> {
                    int row = editRow;
                    if (row != ranges.size() - 1) {
                        String end = ranges.get(row).getEnd();
                        if (!end.isEmpty() && end.charAt(0) != 'P') {
                            ranges.get(row).setEnd("PhP " + end);
                            philhealthTv.refresh();
                        }
                    }
                }
        );
        ph_value.setOnEditCancel(
                t -> {
                    int row = editRow;
                    String value = ranges.get(row).getValue();
                    if (row == 0 || row == ranges.size() - 1) {
                        if (!value.isEmpty() && value.charAt(0) != 'P') {
                            ranges.get(row).setValue("PhP " + value);
                            philhealthTv.refresh();
                        }
                    } else {
                        if (!value.isEmpty() && value.charAt(value.length() - 1) != '%') {
                            ranges.get(row).setValue(value + " %");
                            philhealthTv.refresh();
                        }
                    }
                }
        );

        // check for errors and add symbols back on edit commit
        ph_start.setOnEditCommit(
                t -> {
                    int row = t.getTablePosition().getRow();
                    try {
                        double check = Double.parseDouble(t.getNewValue());
                        if (check <= 0 || !checkDecimalPlaces(t.getNewValue())) {
                            throw new Exception();
                        }
                        ranges.get(row).setStart("PhP " + df.format(check));
                    } catch (Exception e) {
                        ph_errorText.setText("All inputs must be positive values with up to 2 decimal places only!");
                        ph_errorText.setVisible(true);

                        String start = ranges.get(row).getStart();
                        if (!start.isEmpty()) {
                            ranges.get(row).setStart("PhP " + start);
                        }
                    }
                    philhealthTv.refresh();
                }
        );
        ph_end.setOnEditCommit(
                t -> {
                    int row = t.getTablePosition().getRow();
                    try {
                        double check = Double.parseDouble(t.getNewValue());
                        if (check <= 0 || !checkDecimalPlaces(t.getNewValue())) {
                            throw new Exception();
                        }
                        ranges.get(row).setEnd("PhP " + df.format(check));
                    } catch (Exception e) {
                        ph_errorText.setText("All inputs must be positive values with up to 2 decimal places only!");
                        ph_errorText.setVisible(true);

                        String end = ranges.get(row).getEnd();
                        if (!end.isEmpty()) {
                            ranges.get(row).setEnd("PhP " + end);
                        }
                    }
                    philhealthTv.refresh();
                }
        );
        ph_value.setOnEditCommit(
                t -> {
                    int row = t.getTablePosition().getRow();
                    try {
                        double check = Double.parseDouble(t.getNewValue());
                        if (check <= 0 || !checkDecimalPlaces(t.getNewValue())) {
                            throw new Exception("Regular");
                        }
                        if (row != 0 && row != ranges.size() - 1) {
                            if (check >= 100) {
                                throw new Exception("Percent");
                            }
                            ranges.get(row).setValue(df.format(check) + " %");
                        } else {
                            ranges.get(row).setValue("PhP " + df.format(check));
                        }
                    } catch (Exception e) {
                        ph_errorText.setText("All inputs must be positive values with up to 2 decimal places only!");
                        if (e.getMessage().equals("Percent")) {
                            ph_errorText.setText("All percentages must be greater than 0 and less than 100!");
                        }
                        ph_errorText.setVisible(true);

                        String value = ranges.get(row).getValue();
                        if (!value.isEmpty()) {
                            if (row == 0 || row == ranges.size() - 1) {
                                ranges.get(row).setValue("PhP " + value);
                            } else {
                                ranges.get(row).setValue(value + " %");
                            }
                        }
                    }
                    philhealthTv.refresh();
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
        philhealthTv.edit(-1, null);
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
                ranges.add(new PhilHealthRange("PhP " + df.format(start),
                        "PhP " + df.format(end), df.format(value) + " %"));
            } else if (i == 0) {
                ranges.add(new PhilHealthRange("PhP " + df.format(start),
                        "PhP " + df.format(end), "PhP " + df.format(value)));
            } else {
                ranges.add(new PhilHealthRange("PhP " + df.format(start),
                        "MAX", "PhP " + df.format(value)));
            }
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
                // remove ranges that are all empty
                try {
                    ArrayList<PhilHealthRange> newRanges = new ArrayList<>();
                    for (PhilHealthRange range : ranges) {
                        if (range.getStart().isEmpty() && range.getEnd().isEmpty() &&
                                range.getValue().isEmpty()) {
                            continue;
                        }
                        newRanges.add(range);
                    }
                    ranges.setAll(newRanges);


                    // check ranges that are incomplete
                    newRanges = new ArrayList<>();
                    for (PhilHealthRange range : ranges) {
                        if (range.getStart().isEmpty() || range.getEnd().isEmpty() ||
                                range.getValue().isEmpty()) {
                            ph_errorText.setText("All cells must be nonempty!");
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
                    int rangeCount = newRanges.size();
                    for (int i = 0; i < rangeCount - 1; i++) {
                        ArrayList<Double> rangeA = ranges.get(i).convert();
                        ArrayList<Double> rangeB = ranges.get(i + 1).convert();

                        double start = rangeB.get(0);
                        double end = rangeA.get(1);

                        if (start != end + 0.01) {
                            ph_errorText.setText("Ranges must cover all possible values for salary!");
                            ph_errorText.setVisible(true);
                            return;
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
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
}
