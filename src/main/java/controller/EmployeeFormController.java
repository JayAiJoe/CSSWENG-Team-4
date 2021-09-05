package controller;

import dao.EmployeePOJO;
import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.EmployeeForm;

import java.util.Optional;

public class EmployeeFormController extends Controller {

    @FXML
    private ChoiceBox modeCb, companyCb;

    @FXML
    private Button saveBtn, cancelBtn;


    /**
     * initialization of error text  and textfields in EmployeeForm.fxml
     */
    @FXML
    private Text nameErrorText, companyErrorText, modeErrorText,frequencyErrorText,
                wageErrorText;
    @FXML
    private TextField nameTf, frequencyTf, wageTf;

    private EmployeeForm employeeForm;

    @Override
    public void update() {

    }

    public void initialize(){

        //limit input to numbers only
        wageTf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d+(\\.\\d+)")) {
                wageTf.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void hideErrorText(){
        modeErrorText.setVisible(false);
        nameErrorText.setVisible(false);
        companyErrorText.setVisible(false);
        frequencyErrorText.setVisible(false);
        wageErrorText.setVisible(false);
    }

    public void onSaveBtnClick(){
        hideErrorText();
        boolean check = true;
        if(nameTf.getText().equals("")){
            nameErrorText.setText("Name should be filled!");
            nameErrorText.setVisible(true);
            check = false;
        }
        if(frequencyTf.getText().equals("")){
            frequencyErrorText.setText("Frequency of Payment should be filled!");
            frequencyErrorText.setVisible(true);
            check = false;
        }
        if(wageTf.getText().equals("")){
            wageErrorText.setText("Wage should be filled!");
            wageErrorText.setVisible(true);
            check = false;
        }
        if(companyCb.getSelectionModel().isEmpty()){
            companyErrorText.setText("No company selected!");
            companyErrorText.setVisible(true);
            check = false;
        }
        if(modeCb.getSelectionModel().isEmpty()){
            modeErrorText.setText("No mode selected!");
            modeErrorText.setVisible(true);
            check = false;
        }

        if(check){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Add this Employee?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                employeeForm = new EmployeeForm();
                boolean acknowledged = employeeForm.addEmployee(nameTf.getText(),frequencyTf.getText(),Double.parseDouble(wageTf.getText()),
                                                                companyCb.getValue().toString(),modeCb.getValue().toString());
                if (acknowledged){
                    System.out.println("Employee added");
                }
                else{
                    System.out.println("Add Employee failed");
                }
                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();

            } else {

            }
        }
    }

    public void onCancelBtnClick(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Cancel adding of employee?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        } else {

        }
    }


}