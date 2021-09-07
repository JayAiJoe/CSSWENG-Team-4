package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.WorkdayHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ApprovedOvertimeFormController {

    @FXML
    private Button saveBtn, cancelBtn;

    /**
     * initialization of error text  and textfields in EmployeeForm.fxml
     */
    @FXML
    private Text dateText, overtimeStartErrorText, workHours1ErrorText, workHours2ErrorText, overtimeEndErrorText;
    @FXML
    private TextField overtimeStartTf, overtimeEndTf, firstHalfStartTf, firstHalfEndTf, secondHalfStartTf,
    secondHalfEndTf;

    private Date date;
    private WorkdayHandler model;

    @FXML
    public void initialize() {
        hideErrorText();
        //limit input to numbers only
        initTextField(overtimeStartTf);
        initTextField(overtimeEndTf);
        initTextField(firstHalfStartTf);
        initTextField(firstHalfEndTf);
        initTextField(secondHalfStartTf);
        initTextField(secondHalfEndTf);

        Date today = new Date();
        date = new Date(today.getTime() + 86400000L);
        dateText.setText(new SimpleDateFormat("MM/dd/yyyy").format(date));
    }

    private void initTextField(TextField tf) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d*")) {
                tf.setText(oldValue);
            }
        });
    }

    public void setModel(WorkdayHandler model) {
        this.model = model;
    }

    private void hideErrorText() {
        overtimeStartErrorText.setVisible(false);
        workHours1ErrorText.setVisible(false);
        workHours2ErrorText.setVisible(false);
        overtimeEndErrorText.setVisible(false);
    }

    private boolean isInvalidTime(String input) {
        if (input.equals("")) {
            return false;
        }

        int time = Integer.parseInt(input);
        int hour = time / 100, minute = time % 100;
        return hour >= 24 || minute >= 60;
    }

    @FXML
    private void onSaveAction() {
        hideErrorText();

        boolean check = true;
        if (firstHalfStartTf.getText().equals("") || firstHalfEndTf.getText().equals("")) {
            workHours1ErrorText.setText("Work Hours First Half should be filled!");
            workHours1ErrorText.setVisible(true);
            check = false;
        } else if (isInvalidTime(firstHalfStartTf.getText()) || isInvalidTime(firstHalfEndTf.getText())) {
            workHours1ErrorText.setText("Work Hours First Half should be in valid 24-hour time format!");
            workHours1ErrorText.setVisible(true);
            check = false;
        } else {
            int start = Integer.parseInt(firstHalfStartTf.getText());
            int end = Integer.parseInt(firstHalfEndTf.getText());
            if (start >= end) {
                workHours1ErrorText.setText("Start time should be before end time!");
                workHours1ErrorText.setVisible(true);
                check = false;
            }
        }

        if ((secondHalfStartTf.getText().equals("") && !secondHalfEndTf.getText().equals("")) ||
                (!secondHalfStartTf.getText().equals("") && secondHalfEndTf.getText().equals(""))) {
            workHours2ErrorText.setText("Work Hours Second Half should be empty or filled!");
            workHours2ErrorText.setVisible(true);
            check = false;
        } else if (isInvalidTime(secondHalfStartTf.getText()) || isInvalidTime(secondHalfEndTf.getText())) {
            workHours2ErrorText.setText("Work Hours Second Half should be in valid 24-hour time format!");
            workHours2ErrorText.setVisible(true);
            check = false;
        } else if (!secondHalfStartTf.getText().equals("") && !secondHalfEndTf.getText().equals("")) {
            int start = Integer.parseInt(secondHalfStartTf.getText());
            int end = Integer.parseInt(secondHalfEndTf.getText());
            if (start >= end) {
                workHours2ErrorText.setText("Start time should be before end time!");
                workHours2ErrorText.setVisible(true);
                check = false;
            }
        }

        if (isInvalidTime(overtimeStartTf.getText())) {
            overtimeStartErrorText.setText("Overtime Start Time should be in valid 24-hour time format!");
            overtimeStartErrorText.setVisible(true);
            check = false;
        }
        if (isInvalidTime(overtimeEndTf.getText())) {
            overtimeEndErrorText.setText("Overtime End Time should be in valid 24-hour time format!");
            overtimeEndErrorText.setVisible(true);
            check = false;
        }
        if (!check) {
            return;
        }

        int timeIn1, timeOut1, timeIn2 = 0, timeOut2 = 0, overtimeIn = 0, overtimeOut = 0;

        timeIn1 = Integer.parseInt(firstHalfStartTf.getText());
        int end = Integer.parseInt(firstHalfEndTf.getText());
        timeOut1 = end;
        if (!secondHalfStartTf.getText().equals("") && !secondHalfEndTf.getText().equals("")) {
            timeIn2 = Integer.parseInt(secondHalfStartTf.getText());
            timeOut2 = Integer.parseInt(secondHalfEndTf.getText());
            if (timeIn2 < end) {
                workHours2ErrorText.setText("Work Hours Second Half should start after First Half!");
                workHours2ErrorText.setVisible(true);
                return;
            }
            end = timeOut2;
        }

        if (!overtimeStartTf.getText().equals("")) {
            overtimeIn = Integer.parseInt(overtimeStartTf.getText());
            if (overtimeIn > timeIn1) {
                overtimeStartErrorText.setText("Overtime Start Time should be before Work Hours!");
                overtimeStartErrorText.setVisible(true);
                check = false;
            }
        }
        if (!overtimeEndTf.getText().equals("")) {
            overtimeOut = Integer.parseInt(overtimeEndTf.getText());
            if (overtimeOut < end) {
                overtimeEndErrorText.setText("Overtime End Time should be after Work Hours!");
                overtimeEndErrorText.setVisible(true);
                check = false;
            }
        }

        if (check) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Add approved overtime and work hours?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean acknowledged = model.addWorkday(date, timeIn1, timeOut1, timeIn2,
                        timeOut2, overtimeIn, overtimeOut);

                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setGraphic(null);

                if (acknowledged) {
                    System.out.println("Workday added");
                    alert.setTitle("Success");
                    alert.setContentText("Approved overtime and work hours added");
                } else {
                    System.out.println("Add Workday failed");
                    alert.setTitle("Error");
                    alert.setContentText("Failed to add approved overtime and work hours");
                }

                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onCancelAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Cancel adding of approved overtime and work hours?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }
    }
}
