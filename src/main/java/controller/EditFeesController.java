package controller;

import driver.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private AnchorPane navBar_container;

    /**
     * Instantiation of Button objects from EditFees.fxml
     */
    @FXML
    private Button pi_edit_btn, pi_cancel_btn,
            pi_save_btn, ph_update_btn, ph_cancel_btn, ph_add_btn, ph_delete_btn,
            ph_save_btn, sss_edit_btn, sss_save_btn, sss_cancel_btn, sss_add_btn,
            sss_remove_btn;

    @FXML
    private TextField pi_employeeTf, pi_employerTf;
    @FXML
    private Text pi_employer_errorText, pi_employee_errorText, ph_errorText, sss_errorText;

    /**
     * Instantiation of objects related to table from EditFees.fxml
     */
    @FXML
    TableView<PhilHealthRange> philhealthTv;
    @FXML
    private TableColumn<PhilHealthRange, String> ph_start, ph_end, ph_value, ph_range;
    @FXML
    TableView<SSSRange> sssTv;
    @FXML
    private TableColumn<SSSRange, String> sss_startTc, sss_endTc, sss_EETc, sss_ERTc,
        sss_range, sss_value1, sss_value2;

    /**
     * Model components that are necessary for editing
     * the government fees.
     */
    private PagIbigFee pagIbigFee;
    private FeeTable philhealthFeeTable, sssFeeTable, employeeCompensation;
    private ObservableList<PhilHealthRange> philHealthRanges;
    private ObservableList<SSSRange> sssRanges;
    private int ph_editRow, sss_editRow;

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        resetPagIbig();
        resetPhilHealth();
        resetSSS();
    }

    @FXML
    public void initialize() {
        resetPagIbig();
        resetPhilHealth();
        resetSSS();

        // Pag-Ibig initialization
        pi_employeeTf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("^\\d+(\\.\\d*)?")) {
                pi_employeeTf.setText(oldValue);
            }
        });
        pi_employerTf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("^\\d+(\\.\\d*)?")) {
                pi_employerTf.setText(oldValue);
            }
        });

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

        // SSS initialization
        sss_startTc.setCellValueFactory(new PropertyValueFactory<>("start"));
        sss_endTc.setCellValueFactory(new PropertyValueFactory<>("end"));
        sss_EETc.setCellValueFactory(new PropertyValueFactory<>("compensation"));
        sss_ERTc.setCellValueFactory(new PropertyValueFactory<>("value"));
        sss_startTc.setCellFactory(TextFieldTableCell.forTableColumn());
        sss_endTc.setCellFactory(TextFieldTableCell.forTableColumn());
        sss_EETc.setCellFactory(TextFieldTableCell.forTableColumn());
        sss_ERTc.setCellFactory(TextFieldTableCell.forTableColumn());
        sss_startTc.setReorderable(false);
        sss_endTc.setReorderable(false);
        sss_EETc.setReorderable(false);
        sss_ERTc.setReorderable(false);
        sss_range.setReorderable(false);
        sss_value1.setReorderable(false);
        sss_value2.setReorderable(false);

        // remove symbols on edit start
        ph_start.setOnEditStart(t -> {
            int row = t.getTablePosition().getRow();
            ph_editRow = row;
            if (row == 0) {
                philhealthTv.edit(-1, null);
            } else {
                String start = philHealthRanges.get(row).getStart();
                if (!start.isEmpty() && start.charAt(0) == 'P') {
                    philHealthRanges.get(row).setStart(start.substring(4));
                    philhealthTv.refresh();
                }
                ph_errorText.setVisible(false);
            }
        });
        ph_end.setOnEditStart(t -> {
            int row = t.getTablePosition().getRow();
            ph_editRow = row;
            if (row == philHealthRanges.size() - 1) {
                philhealthTv.edit(-1, null);
            } else {
                String end = philHealthRanges.get(row).getEnd();
                if (!end.isEmpty() && end.charAt(0) == 'P') {
                    philHealthRanges.get(row).setEnd(end.substring(4));
                    philhealthTv.refresh();
                }
                ph_errorText.setVisible(false);
            }
        });
        ph_value.setOnEditStart(t -> {
            int row = t.getTablePosition().getRow();
            ph_editRow = row;
            String value = philHealthRanges.get(row).getValue();
            if (row == 0 || row == philHealthRanges.size() - 1) {
                if (!value.isEmpty() && value.charAt(0) == 'P') {
                    philHealthRanges.get(row).setValue(value.substring(4));
                    philhealthTv.refresh();
                }
            } else {
                if (!value.isEmpty() && value.charAt(value.length() - 1) == '%') {
                    philHealthRanges.get(row).setValue(value.substring(0, value.length() - 2));
                    philhealthTv.refresh();
                }
            }
            ph_errorText.setVisible(false);
        });
        sss_startTc.setOnEditStart(t -> {
            int row = t.getTablePosition().getRow();
            sss_editRow = row;
            if (row == 0) {
                sssTv.edit(-1, null);
            } else {
                String start = sssRanges.get(row).getStart();
                if (!start.isEmpty() && start.charAt(0) == 'P') {
                    sssRanges.get(row).setStart(start.substring(4));
                    sssTv.refresh();
                }
                sss_errorText.setVisible(false);
            }
        });
        sss_endTc.setOnEditStart(t -> {
            int row = t.getTablePosition().getRow();
            sss_editRow = row;
            if (row == sssRanges.size() - 1) {
                sssTv.edit(-1, null);
            } else {
                String end = sssRanges.get(row).getEnd();
                if (!end.isEmpty() && end.charAt(0) == 'P') {
                    sssRanges.get(row).setEnd(end.substring(4));
                    sssTv.refresh();
                }
                sss_errorText.setVisible(false);
            }
        });
        sss_EETc.setOnEditStart(t -> {
            int row = t.getTablePosition().getRow();
            sss_editRow = row;
            String compensation = sssRanges.get(row).getCompensation();
            if (!compensation.isEmpty() && compensation.charAt(0) == 'P') {
                sssRanges.get(row).setCompensation(compensation.substring(4));
                sssTv.refresh();
            }
            sss_errorText.setVisible(false);
        });
        sss_ERTc.setOnEditStart(t -> {
            int row = t.getTablePosition().getRow();
            sss_editRow = row;
            String value = sssRanges.get(row).getValue();
            if (!value.isEmpty() && value.charAt(0) == 'P') {
                sssRanges.get(row).setValue(value.substring(4));
                sssTv.refresh();
            }
            sss_errorText.setVisible(false);
        });

        // add symbols back on edit cancel
        ph_start.setOnEditCancel(t -> {
            int row = ph_editRow;
            if (row != 0) {
                String start = philHealthRanges.get(row).getStart();
                if (!start.isEmpty() && start.charAt(0) != 'P') {
                    philHealthRanges.get(row).setStart("PhP " + start);
                    philhealthTv.refresh();
                }
            }
        });
        ph_end.setOnEditCancel(t -> {
            int row = ph_editRow;
            if (row != philHealthRanges.size() - 1) {
                String end = philHealthRanges.get(row).getEnd();
                if (!end.isEmpty() && end.charAt(0) != 'P') {
                    philHealthRanges.get(row).setEnd("PhP " + end);
                    philhealthTv.refresh();
                }
            }
        });
        ph_value.setOnEditCancel(t -> {
            int row = ph_editRow;
            String value = philHealthRanges.get(row).getValue();
            if (row == 0 || row == philHealthRanges.size() - 1) {
                if (!value.isEmpty() && value.charAt(0) != 'P') {
                    philHealthRanges.get(row).setValue("PhP " + value);
                    philhealthTv.refresh();
                }
            } else {
                if (!value.isEmpty() && value.charAt(value.length() - 1) != '%') {
                    philHealthRanges.get(row).setValue(value + " %");
                    philhealthTv.refresh();
                }
            }
        });
        sss_startTc.setOnEditCancel(t -> {
            int row = sss_editRow;
            if (row != 0) {
                String start = sssRanges.get(row).getStart();
                if (!start.isEmpty() && start.charAt(0) != 'P') {
                    sssRanges.get(row).setStart("PhP " + start);
                    sssTv.refresh();
                }
            }
        });
        sss_endTc.setOnEditCancel(t -> {
            int row = sss_editRow;
            if (row != sssRanges.size() - 1) {
                String end = sssRanges.get(row).getEnd();
                if (!end.isEmpty() && end.charAt(0) != 'P') {
                    sssRanges.get(row).setEnd("PhP " + end);
                    sssTv.refresh();
                }
            }
        });
        sss_EETc.setOnEditCancel(t -> {
            int row = sss_editRow;
            String compensation = sssRanges.get(row).getCompensation();
            if (!compensation.isEmpty() && compensation.charAt(0) != 'P') {
                sssRanges.get(row).setCompensation("PhP " + compensation);
                sssTv.refresh();
            }
        });
        sss_ERTc.setOnEditCancel(t -> {
            int row = sss_editRow;
            String value = sssRanges.get(row).getValue();
            if (!value.isEmpty() && value.charAt(0) != 'P') {
                sssRanges.get(row).setValue("PhP " + value);
                sssTv.refresh();
            }
        });

        // check for errors and add symbols back on edit commit
        ph_start.setOnEditCommit(t -> {
            int row = t.getTablePosition().getRow();
            try {
                double check = Double.parseDouble(t.getNewValue());
                if (check <= 0 || !checkDecimalPlaces(t.getNewValue())) {
                    throw new Exception();
                }
                philHealthRanges.get(row).setStart("PhP " + df.format(check));
            } catch (Exception e) {
                ph_errorText.setText("All inputs must be positive values with up to 2 decimal places only!");
                ph_errorText.setVisible(true);

                String start = philHealthRanges.get(row).getStart();
                if (!start.isEmpty()) {
                    philHealthRanges.get(row).setStart("PhP " + start);
                }
            }
            philhealthTv.refresh();
        });
        ph_end.setOnEditCommit(t -> {
            int row = t.getTablePosition().getRow();
            try {
                double check = Double.parseDouble(t.getNewValue());
                if (check <= 0 || !checkDecimalPlaces(t.getNewValue())) {
                    throw new Exception();
                }
                philHealthRanges.get(row).setEnd("PhP " + df.format(check));
            } catch (Exception e) {
                ph_errorText.setText("All inputs must be positive values with up to 2 decimal places only!");
                ph_errorText.setVisible(true);

                String end = philHealthRanges.get(row).getEnd();
                if (!end.isEmpty()) {
                    philHealthRanges.get(row).setEnd("PhP " + end);
                }
            }
            philhealthTv.refresh();
        });
        ph_value.setOnEditCommit(t -> {
            int row = t.getTablePosition().getRow();
            try {
                double check = Double.parseDouble(t.getNewValue());
                if (check <= 0 || !checkDecimalPlaces(t.getNewValue())) {
                    throw new Exception("Regular");
                }
                if (row != 0 && row != philHealthRanges.size() - 1) {
                    if (check >= 100) {
                        throw new Exception("Percent");
                    }
                    philHealthRanges.get(row).setValue(df.format(check) + " %");
                } else {
                    philHealthRanges.get(row).setValue("PhP " + df.format(check));
                }
            } catch (Exception e) {
                ph_errorText.setText("All inputs must be positive values with up to 2 decimal places only!");
                if (e.getMessage().equals("Percent")) {
                    ph_errorText.setText("All percentages must be greater than 0 and less than 100!");
                }
                ph_errorText.setVisible(true);

                String value = philHealthRanges.get(row).getValue();
                if (!value.isEmpty()) {
                    if (row == 0 || row == philHealthRanges.size() - 1) {
                        philHealthRanges.get(row).setValue("PhP " + value);
                    } else {
                        philHealthRanges.get(row).setValue(value + " %");
                    }
                }
            }
            philhealthTv.refresh();
        });
        sss_startTc.setOnEditCommit(t -> {
            int row = t.getTablePosition().getRow();
            try {
                double check = Double.parseDouble(t.getNewValue());
                if (check <= 0 || !checkDecimalPlaces(t.getNewValue())) {
                    throw new Exception();
                }
                sssRanges.get(row).setStart("PhP " + df.format(check));
            } catch (Exception e) {
                sss_errorText.setText("All inputs must be positive values with up to 2 decimal places only!");
                sss_errorText.setVisible(true);

                String start = sssRanges.get(row).getStart();
                if (!start.isEmpty()) {
                    sssRanges.get(row).setStart("PhP " + start);
                }
            }
            sssTv.refresh();
        });
        sss_endTc.setOnEditCommit(t -> {
            int row = t.getTablePosition().getRow();
            try {
                double check = Double.parseDouble(t.getNewValue());
                if (check <= 0 || !checkDecimalPlaces(t.getNewValue())) {
                    throw new Exception();
                }
                sssRanges.get(row).setEnd("PhP " + df.format(check));
            } catch (Exception e) {
                sss_errorText.setText("All inputs must be positive values with up to 2 decimal places only!");
                sss_errorText.setVisible(true);

                String end = sssRanges.get(row).getEnd();
                if (!end.isEmpty()) {
                    sssRanges.get(row).setEnd("PhP " + end);
                }
            }
            sssTv.refresh();
        });
        sss_EETc.setOnEditCommit(t -> {
            int row = t.getTablePosition().getRow();
            try {
                double check = Double.parseDouble(t.getNewValue());
                if (check <= 0 || !checkDecimalPlaces(t.getNewValue())) {
                    throw new Exception();
                }
                sssRanges.get(row).setCompensation("PhP " + df.format(check));
            } catch (Exception e) {
                sss_errorText.setText("All inputs must be positive values with up to 2 decimal places only!");
                sss_errorText.setVisible(true);

                String compensation = sssRanges.get(row).getCompensation();
                if (!compensation.isEmpty()) {
                    sssRanges.get(row).setCompensation("PhP " + compensation);
                }
            }
            sssTv.refresh();
        });
        sss_ERTc.setOnEditCommit(t -> {
            int row = t.getTablePosition().getRow();
            try {
                double check = Double.parseDouble(t.getNewValue());
                if (check <= 0 || !checkDecimalPlaces(t.getNewValue())) {
                    throw new Exception();
                }
                sssRanges.get(row).setValue("PhP " + df.format(check));
            } catch (Exception e) {
                sss_errorText.setText("All inputs must be positive values with up to 2 decimal places only!");
                sss_errorText.setVisible(true);

                String value = sssRanges.get(row).getValue();
                if (!value.isEmpty()) {
                    sssRanges.get(row).setValue("PhP " + value);
                }
            }
            sssTv.refresh();
        });


        // get and set data
        resetPhilHealthRanges();
        resetSSSRanges();
    }

    /**
     * Resets the view components related to PagIbig.
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
     * Resets the view components related to Philhealth.
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
        resetPhilHealthRanges();
    }

    /**
     * Resets the view components related to SSS.
     */
    private void resetSSS() {
        // SSS initialization
        sssFeeTable = Calculator.getInstance().getSssFeeTable();
        employeeCompensation = Calculator.getInstance().getEmployeeCompensation();
        sssTv.edit(-1, null);
        sssTv.setEditable(false);
        sss_edit_btn.toFront();
        sss_edit_btn.setDisable(false);
        sss_cancel_btn.toBack();
        sss_cancel_btn.setDisable(true);
        sss_cancel_btn.setVisible(false);
        sss_add_btn.setVisible(false);
        sss_add_btn.setDisable(true);
        sss_remove_btn.setVisible(false);
        sss_remove_btn.setDisable(true);
        sss_save_btn.setVisible(false);
        sss_save_btn.setDisable(true);
        sss_errorText.setVisible(false);
        resetSSSRanges();
    }

    /**
     * Resets the contents of the PhilHealth fee table.
     */
    private void resetPhilHealthRanges() {
        philHealthRanges = FXCollections.observableArrayList();
        ArrayList<ArrayList<Double>> formulas = philhealthFeeTable.getFormulas();
        for (int i = 0; i < formulas.size(); i++) {
            double start = formulas.get(i).get(0);
            double end = formulas.get(i).get(1);
            double value = formulas.get(i).get(2);
            if (i != 0 && i != formulas.size() - 1) {
                value *= 100;
                philHealthRanges.add(new PhilHealthRange("PhP " + df.format(start),
                        "PhP " + df.format(end), df.format(value) + " %"));
            } else if (i == 0) {
                philHealthRanges.add(new PhilHealthRange("PhP " + df.format(start),
                        "PhP " + df.format(end), "PhP " + df.format(value)));
            } else {
                philHealthRanges.add(new PhilHealthRange("PhP " + df.format(start),
                        "MAX", "PhP " + df.format(value)));
            }
        }
        philhealthTv.setItems(philHealthRanges);
    }

    private void resetSSSRanges() {
        sssRanges = FXCollections.observableArrayList();
        ArrayList<ArrayList<Double>> sssFormulas = sssFeeTable.getFormulas();
        ArrayList<ArrayList<Double>> compensationFormulas = employeeCompensation.getFormulas();
        for (int i = 0; i < sssFormulas.size(); i++) {
            double start = sssFormulas.get(i).get(0);
            double end = sssFormulas.get(i).get(1);
            double compensation = compensationFormulas.get(i).get(2);
            double value = sssFormulas.get(i).get(2);
            if (i == sssFormulas.size() - 1) {
                sssRanges.add(new SSSRange("PhP " + df.format(start),
                        "MAX", "PhP " + df.format(compensation),
                        "PhP " + df.format(value)));
            } else {
                sssRanges.add(new SSSRange("PhP " + df.format(start),
                        "PhP " + df.format(end),
                        "PhP " + df.format(compensation),
                        "PhP " + df.format(value)));
            }
        }
        sssTv.setItems(sssRanges);
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
                ArrayList<PhilHealthRange> newRanges = new ArrayList<>();
                for (PhilHealthRange range : philHealthRanges) {
                    if (range.getStart().isEmpty() && range.getEnd().isEmpty() &&
                            range.getValue().isEmpty()) {
                        continue;
                    }
                    newRanges.add(range);
                }
                philHealthRanges.setAll(newRanges);

                // check ranges that are incomplete
                newRanges = new ArrayList<>();
                for (PhilHealthRange range : philHealthRanges) {
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
                philHealthRanges.setAll(newRanges);
                // check all ranges are valid
                int rangeCount = newRanges.size();
                for (int i = 0; i < rangeCount - 1; i++) {
                    ArrayList<Double> rangeA = philHealthRanges.get(i).convert();
                    ArrayList<Double> rangeB = philHealthRanges.get(i + 1).convert();

                    double start = rangeB.get(0);
                    double end = rangeA.get(1);

                    if (start != end + 0.01) {
                        if (start > end) {
                            ph_errorText.setText("Ranges must be connected!");
                        } else {
                            ph_errorText.setText("Ranges must not overlap!");
                        }
                        ph_errorText.setVisible(true);
                        return;
                    }
                }

                // update PhilHealth fee table
                ArrayList<ArrayList<Double>> formulas = new ArrayList<>();
                for (int i = 0; i < rangeCount; i++) {
                    PhilHealthRange range = philHealthRanges.get(i);
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
                philhealthTv.edit(-1, null);
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
                resetPhilHealthRanges();

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
                philhealthTv.edit(-1, null);
                philhealthTv.setEditable(false);
                ph_errorText.setVisible(false);
                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        }
    }

    public void onSSSEditClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == sss_edit_btn) {
            sss_edit_btn.toBack();
            sss_edit_btn.setDisable(true);
            sss_cancel_btn.toFront();
            sss_cancel_btn.setDisable(false);
            sss_cancel_btn.setVisible(true);
            sss_add_btn.setVisible(true);
            sss_add_btn.setDisable(false);
            sss_remove_btn.setVisible(true);
            sss_remove_btn.setDisable(false);
            sss_save_btn.setVisible(true);
            sss_save_btn.setDisable(false);
            sssTv.setEditable(true);
        } else if (mouseEvent.getSource() == sss_save_btn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Apply changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // remove ranges that are all empty
                ArrayList<SSSRange> newRanges = new ArrayList<>();
                for (SSSRange range : sssRanges) {
                    if (range.getStart().isEmpty() && range.getEnd().isEmpty() &&
                            range.getCompensation().isEmpty() && range.getValue().isEmpty()) {
                        continue;
                    }
                    newRanges.add(range);
                }
                sssRanges.setAll(newRanges);

                // check ranges that are incomplete
                newRanges = new ArrayList<>();
                for (SSSRange range : sssRanges) {
                    if (range.getStart().isEmpty() || range.getEnd().isEmpty() ||
                            range.getCompensation().isEmpty() || range.getValue().isEmpty()) {
                        sss_errorText.setText("All cells must be nonempty!");
                        sss_errorText.setVisible(true);
                        return;
                    }
                    newRanges.add(range);
                }

                // sort input ranges
                Collections.sort(newRanges);
                // set new ranges
                sssRanges.setAll(newRanges);
                // check all ranges are valid
                int rangeCount = newRanges.size();
                for (int i = 0; i < rangeCount - 1; i++) {
                    ArrayList<Double> rangeA = sssRanges.get(i).convert();
                    ArrayList<Double> rangeB = sssRanges.get(i + 1).convert();

                    double start = rangeB.get(0);
                    double end = rangeA.get(1);

                    if (start != end + 0.01) {
                        if (start > end) {
                            sss_errorText.setText("Ranges must be connected!");
                        } else {
                            sss_errorText.setText("Ranges must not overlap!");
                        }
                        sss_errorText.setVisible(true);
                        return;
                    }
                }

                // update SSS fee table
                ArrayList<ArrayList<Double>> sssFormulas = new ArrayList<>();
                ArrayList<ArrayList<Double>> compensationFormulas = new ArrayList<>();
                for (int i = 0; i < rangeCount; i++) {
                    SSSRange range = sssRanges.get(i);
                    ArrayList<Double> newRange = range.convert();
                    ArrayList<Double> rangeA = new ArrayList<>();
                    ArrayList<Double> rangeB = new ArrayList<>();

                    rangeA.add(newRange.get(0));
                    rangeA.add(newRange.get(1));
                    rangeA.add(newRange.get(3));
                    rangeB.add(newRange.get(0));
                    rangeB.add(newRange.get(1));
                    rangeB.add(newRange.get(2));

                    sssFormulas.add(rangeA);
                    compensationFormulas.add(rangeB);
                }

                sssFeeTable.setFormulas(sssFormulas);
                employeeCompensation.setFormulas(compensationFormulas);

                sss_edit_btn.toFront();
                sss_edit_btn.setDisable(false);
                sss_cancel_btn.toBack();
                sss_cancel_btn.setDisable(true);
                sss_cancel_btn.setVisible(false);
                sss_add_btn.setVisible(false);
                sss_add_btn.setDisable(true);
                sss_remove_btn.setVisible(false);
                sss_remove_btn.setDisable(true);
                sss_save_btn.setVisible(false);
                sss_save_btn.setDisable(true);
                sss_errorText.setVisible(false);
                sssTv.edit(-1, null);
                sssTv.setEditable(false);
                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        } else if (mouseEvent.getSource() == sss_cancel_btn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Discard changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                resetSSSRanges();

                sss_edit_btn.toFront();
                sss_edit_btn.setDisable(false);
                sss_cancel_btn.toBack();
                sss_cancel_btn.setDisable(true);
                sss_cancel_btn.setVisible(false);
                sss_add_btn.setVisible(false);
                sss_add_btn.setDisable(true);
                sss_remove_btn.setVisible(false);
                sss_remove_btn.setDisable(true);
                sss_save_btn.setVisible(false);
                sss_save_btn.setDisable(true);
                sssTv.edit(-1, null);
                sssTv.setEditable(false);
                sss_errorText.setVisible(false);
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
        int rowCount = philHealthRanges.size();
        philHealthRanges.add(rowCount - 1, new PhilHealthRange());
    }

    /**
     * Deletes the second to the last row from the
     * PhilHealth fee table provided that there are
     * more than 2 rows in the fee table.
     */
    @FXML
    private void onDeleteRowAction() {
        int rowCount = philHealthRanges.size();
        if (rowCount > 2) {
            philHealthRanges.remove(rowCount - 2);
        }
    }

    /**
     * Adds a new row to the SSS fee table.
     */
    @FXML
    private void onAddSSSRowAction(){
        //TODO: Adding of new row to SSS table
    }

    /**
     * Deletes the second to the last row from the
     * SSS fee table provided that there are
     * more than 2 rows in the fee table.
     */
    @FXML
    private void onDeleteSSSRowAction(){
        //TODO: Removing of 2nd to the last row of sss table
    }
}
