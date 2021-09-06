package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.EmployeeForm;

import java.util.Optional;

public class ApprovedOvertimeFormController extends Controller{

    @FXML
    private Button saveBtn, cancelBtn;

    /**
     * initialization of error text  and textfields in EmployeeForm.fxml
     */
    @FXML
    private Text overtimeErrorText, workHours1ErrorText, workHours2ErrorText, dateText;
    @FXML
    private TextField overtimeStartTf, overtimeEndTf , firstHalfStartTf, firstHalfEndTf, secondHalfStartTf,
    secondHalfEndTf;



    @Override
    public void update() {

    }

    public void initialize() {

    }


    private void hideErrorText() {
        overtimeErrorText.setVisible(false);
        workHours1ErrorText.setVisible(false);
        workHours2ErrorText.setVisible(false);
    }

    public void onSaveAction() {
        hideErrorText();
        boolean check = true;
        if (overtimeStartTf.getText().equals("") || overtimeEndTf.getText().equals("")) {
            overtimeErrorText.setText("Overtime Range should be filled!");
            overtimeErrorText.setVisible(true);
            check = false;
        }
        if (firstHalfStartTf.getText().equals("") || firstHalfEndTf.getText().equals("")) {
            workHours1ErrorText.setText("Work Hours First Half should be filled!");
            workHours1ErrorText.setVisible(true);
            check = false;
        }
        if (secondHalfStartTf.getText().equals("") || secondHalfEndTf.getText().equals("")) {
            workHours2ErrorText.setText("Work Hours Second Half should be filled!");
            workHours2ErrorText.setVisible(true);
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

                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void onCancelAction() {
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
