package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.EmployeeForm;
import wrapper.EmployeeWrapper;

import java.util.Date;
import java.util.Optional;

public class EmployeeEditController extends Controller {
    @FXML
    private Button editBtn, removeBtn, saveBtn;

    /**
     * initialization of error text  and textfields in EmployeeEdit.fxml
     */
    @FXML
    private Text nameErrorText, wageErrorText;
    @FXML
    private TextField nameTf, wageTf;

    /**
     * initialization of employee info text in EmployeeEdit.fxml
     */
    @FXML
    private Text companyTf, modeTf, frequencyTf;

    private EmployeeForm employeeForm;
    private EmployeeWrapper employee;

    @Override
    public void update() {
        editBtn.setText("Edit");
        setTextFieldsStatus(true);
        hideErrorText();
        removeBtn.setVisible(true);

        resetEmployee();
    }

    @FXML
    public void initialize() {
        editBtn.setText("Edit");
        setTextFieldsStatus(true);
        hideErrorText();
        removeBtn.setVisible(true);

        //limit input to numbers only
        wageTf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("^\\d+(\\.\\d*)?")) {
                wageTf.setText(oldValue);
            }
        });
    }

    public void setModels(EmployeeForm employeeForm, EmployeeWrapper employee) {
        this.employeeForm = employeeForm;
        this.employee = employee;
    }

    private void resetEmployee() {
        companyTf.setText(employee.getCompanyFull());
        modeTf.setText(employee.getMode());
        frequencyTf.setText(employee.getWageFrequency());

        nameTf.setText(employee.getCompleteName());
        wageTf.setText(employee.getWageString());
    }

    private void hideErrorText() {
        nameErrorText.setVisible(false);
        wageErrorText.setVisible(false);
    }

    private void setTextFieldsStatus(boolean status) {
        nameTf.setDisable(status);
        wageTf.setDisable(status);
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
     * functionality for editBtn
     */
    @FXML
    private void onEditAction() {
        saveBtn.setVisible(true);
        removeBtn.setVisible(false);
        setTextFieldsStatus(false);

    }

    public void onSaveAction(){
        hideErrorText();
        boolean check = true;
        if (nameTf.getText().equals("")) {
            nameErrorText.setText("Name should be filled!");
            nameErrorText.setVisible(true);
            check = false;
        }

        if (wageTf.getText().equals("")) {
            wageErrorText.setText("Wage should be filled!");
            wageErrorText.setVisible(true);
            check = false;
        } else {
            double wage = Double.parseDouble(wageTf.getText());
            if (wage <= 0 || !checkDecimalPlaces(wageTf.getText())) {
                wageErrorText.setText("Wage should be a positive value with up to 2 decimal places only!");
                wageErrorText.setVisible(true);
                check = false;
            }
        }

        if (check) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);

            String alertText = "Save Changes?";
            double wage = Double.parseDouble(wageTf.getText());
            if (wage < employee.getWage()) {
                alertText += "\n\nWarning: New wage is less than previous wage!";
            }
            alert.setContentText(alertText);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                employee.getEmployee().setCompleteName(nameTf.getText());
                employee.getEmployee().setWage(Double.parseDouble(wageTf.getText()));
                employeeForm.updateEmployee(employee);
                resetEmployee();

                saveBtn.setVisible(false);
                setTextFieldsStatus(true);
                removeBtn.setVisible(true);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.setContentText("Employee has been updated");
                alert.showAndWait();
            }
        }

    }

    /**
     * functionality for cancelBtn
     */
    @FXML
    private void onCancelAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Cancel Changes?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            saveBtn.setVisible(false);
            setTextFieldsStatus(true);
            hideErrorText();
            removeBtn.setVisible(true);

            resetEmployee();
        }
    }

    /**
     * functionality for removeBtn
     */
    @FXML
    private void onRemoveAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Proceed with the removal of this employee?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            setTextFieldsStatus(true);

            employee.getEmployee().setDateLeft(new Date());
            employeeForm.updateEmployee(employee);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Employee has been removed");
            alert.showAndWait();

            onBackAction();
        }
    }

    /**
     * functionality for backBtn
     */
    @FXML
    private void onBackAction() {
        Driver.getScreenController().activate("Employees");
    }
}
