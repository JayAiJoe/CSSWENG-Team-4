package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

public class EmployeeEditController extends Controller{

    @FXML
    private Button editBtn, cancelBtn, removeBtn, backBtn;

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

    @Override
    public void update() {
        disableTextfield();
        hideErrorText();
        removeBtn.setVisible(true);
        
    }

    public void initialize() {
        disableTextfield();
        hideErrorText();
        removeBtn.setVisible(true);
        //limit input to numbers only
        wageTf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d+(\\.\\d+)")) {
                wageTf.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void hideErrorText() {
        nameErrorText.setVisible(false);
        wageErrorText.setVisible(false);
    }

    private void disableTextfield(){
        nameTf.setDisable(true);
        wageTf.setDisable(true);
    }

    /**
     * functionality for editBtn
     */
    public void onEditClick(){
        //on edit btn click
        if(editBtn.getText().equals("Edit")){
            editBtn.setText("Save");
            removeBtn.setVisible(false);
            wageTf.setDisable(false);
            nameTf.setDisable(false);
        }
        //on save button click
        else if (editBtn.getText().equals("Save")){
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
            }

            if (check) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.setContentText("Save Changes?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                }
                    editBtn.setText("Edit");
                    disableTextfield();

                    //TODO save changes to employees
                }
        }
    }

    /**
     * functionality for cancelBtn
     */
    public void onCancelBtnClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Cancel Changes?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            disableTextfield();
            hideErrorText();
            removeBtn.setVisible(true);

            //TODO undo changes in textfields
        }
    }


    /**
     * functionality for removeBtn
     */
    public void onRemoveBtnClick(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Proceed with firing of this employee?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            disableTextfield();
            //TODO remove employee from list
        }
    }

    /**
     * functionality for backBtn
     */
    public void onBackClick(){
        disableTextfield();
        hideErrorText();
        removeBtn.setVisible(true);
        Driver.getScreenController().activate("Employees");
    }
}
