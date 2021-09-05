package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.EmployeeForm;

import java.util.Optional;

public class EmployeeFormController extends Controller {
    @FXML
    private ChoiceBox modeCb, companyCb, frequencyCb;

    @FXML
    private Button saveBtn, cancelBtn;

    /**
     * initialization of error text  and textfields in EmployeeForm.fxml
     */
    @FXML
    private Text nameErrorText, companyErrorText, modeErrorText, frequencyErrorText,
            wageErrorText;
    @FXML
    private TextField nameTf, wageTf;

    private EmployeeForm employeeForm;

    @Override
    public void update() {

    }

    public void initialize() {
        //limit input to numbers only
        wageTf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d+(\\.\\d+)")) {
                wageTf.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void setEmployeeForm(EmployeeForm employeeForm) {
        this.employeeForm = employeeForm;
    }

    private void hideErrorText() {
        modeErrorText.setVisible(false);
        nameErrorText.setVisible(false);
        companyErrorText.setVisible(false);
        frequencyErrorText.setVisible(false);
        wageErrorText.setVisible(false);
    }

    public void onSaveBtnClick() {
        hideErrorText();
        boolean check = true;
        if (nameTf.getText().equals("")) {
            nameErrorText.setText("Name should be filled!");
            nameErrorText.setVisible(true);
            check = false;
        }
        if (frequencyCb.getSelectionModel().isEmpty()) {
            frequencyErrorText.setText("No frequency of payment selected!");
            frequencyErrorText.setVisible(true);
            check = false;
        }
        if (wageTf.getText().equals("")) {
            wageErrorText.setText("Wage should be filled!");
            wageErrorText.setVisible(true);
            check = false;
        }
        if (companyCb.getSelectionModel().isEmpty()) {
            companyErrorText.setText("No company selected!");
            companyErrorText.setVisible(true);
            check = false;
        }
        if (modeCb.getSelectionModel().isEmpty()) {
            modeErrorText.setText("No mode selected!");
            modeErrorText.setVisible(true);
            check = false;
        }

        if (check) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Add this Employee?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean acknowledged = employeeForm.addEmployee(nameTf.getText(),
                        frequencyCb.getValue().toString().toUpperCase(),
                        Double.parseDouble(wageTf.getText()),
                        companyCb.getValue().toString(),
                        modeCb.getValue().toString().toUpperCase());

                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setGraphic(null);

                if (acknowledged) {
                    System.out.println("Employee added");
                    alert.setTitle("Success");
                    alert.setContentText("Employee added");
                } else {
                    System.out.println("Add Employee failed");
                    alert.setTitle("Error");
                    alert.setContentText("Failed to add employee");
                }
            }
        }
    }

    public void onCancelBtnClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Cancel adding of employee?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }
    }
}