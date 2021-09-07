package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.WorkdayHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ApprovedOvertimeWorkHoursController extends Controller{

    @FXML
    private Button nextEditBtn, currentEditBtn, nextCancelBtn, currentCancelBtn;

    /**
     * initialization of error text  and textfields in EmployeeForm.fxml
     */
    @FXML
    private Text dateText, cOvertimeStartErrorText,cShift1ErrorText, cShift2ErrorText, cOvertimeEndErrorText,
            nOvertimeStartErrorText,nShift1ErrorText, nShift2ErrorText, nOvertimeEndErrorText;;
    @FXML
    private TextField cOvertimeStartTf, cOvertimeEndTf, nOvertimeStartTf, nOvertimeEndTf;
    @FXML
    private TextField cFirstHalfStartTf, cFirstHalfEndTf, nFirstHalfStartTf, nFirstHalfEndTf;

    @FXML
    private TextField cSecondHalfStartTf, cSecondHalfEndTf, nSecondHalfStartTf, nSecondHalfEndTf;

    private Date date;
    private WorkdayHandler model;

    @Override
    public void update() {

    }

    @FXML
    public void initialize() {
        hideCurrentErrorText();
        hideNextErrorText();
        setNextTextFieldsStatus(true);
        setCurrentTextFieldsStatus(true);

        //limit input to numbers only
        initTextField(cOvertimeStartTf);
        initTextField(cOvertimeEndTf);
        initTextField(cFirstHalfStartTf);
        initTextField(cFirstHalfEndTf);
        initTextField(cSecondHalfStartTf);
        initTextField(cSecondHalfEndTf);

        initTextField(nOvertimeStartTf);
        initTextField(nOvertimeEndTf);
        initTextField(nFirstHalfStartTf);
        initTextField(nFirstHalfEndTf);
        initTextField(nSecondHalfStartTf);
        initTextField(nSecondHalfEndTf);


        Date today = new Date();
        date = new Date(today.getTime() + 86400000L);
        dateText.setText(new SimpleDateFormat("MM/dd/yyyy").format(date));
    }

    private void initTextField(TextField tf) {
        /*tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d*")) {
                tf.setText(oldValue);
            }
        });*/
    }

    public void setModel(WorkdayHandler model) {
        this.model = model;
    }

    private void hideNextErrorText() {
        nOvertimeStartErrorText.setVisible(false);
        nOvertimeEndErrorText.setVisible(false);
        nShift1ErrorText.setVisible(false);
        nShift2ErrorText.setVisible(false);
    }

    private void hideCurrentErrorText() {
        cOvertimeStartErrorText.setVisible(false);
        cOvertimeEndErrorText.setVisible(false);
        cShift1ErrorText.setVisible(false);
        cShift2ErrorText.setVisible(false);
    }



    private boolean isInvalidTime(String input) {
        if (input.equals("")) {
            return false;
        }

        int time = Integer.parseInt(input);
        int hour = time / 100, minute = time % 100;
        return hour >= 24 || minute >= 60;
    }

    private void setCurrentTextFieldsStatus(boolean status) {
        cOvertimeStartTf.setDisable(status);
        cOvertimeEndTf.setDisable(status);
        cFirstHalfStartTf.setDisable(status);
        cFirstHalfEndTf.setDisable(status);
        cSecondHalfStartTf.setDisable(status);
        cSecondHalfEndTf.setDisable(status);
    }

    private void setNextTextFieldsStatus(boolean status) {
        nOvertimeStartTf.setDisable(status);
        nOvertimeEndTf.setDisable(status);
        nFirstHalfStartTf.setDisable(status);
        nFirstHalfEndTf.setDisable(status);
        nSecondHalfStartTf.setDisable(status);
        nSecondHalfEndTf.setDisable(status);
    }

    @FXML
    private void onNextEditAction() {
        hideNextErrorText();

        if (nextEditBtn.getText().equals("Edit")) { // on edit btn click
            nextEditBtn.setText("Save");
            nextCancelBtn.setVisible(true);
            setNextTextFieldsStatus(false);
        } else if (nextEditBtn.getText().equals("Save")) {
            boolean check = true;
            if (nFirstHalfStartTf.getText().equals("") || nFirstHalfEndTf.getText().equals("")) {
                nShift1ErrorText.setText("Work Hours First Half should be filled!");
                nShift1ErrorText.setVisible(true);
                check = false;
            } else if (isInvalidTime(nFirstHalfStartTf.getText()) || isInvalidTime(nFirstHalfEndTf.getText())) {
                nShift1ErrorText.setText("Work Hours First Half should be in valid 24-hour time format!");
                nShift1ErrorText.setVisible(true);
                check = false;
            } else {
                int start = Integer.parseInt(nFirstHalfStartTf.getText());
                int end = Integer.parseInt(nFirstHalfEndTf.getText());
                if (start >= end) {
                    nShift1ErrorText.setText("Start time should be before end time!");
                    nShift1ErrorText.setVisible(true);
                    check = false;
                }
            }

            if ((nSecondHalfStartTf.getText().equals("") && !nSecondHalfEndTf.getText().equals("")) ||
                    (!nSecondHalfStartTf.getText().equals("") && nSecondHalfEndTf.getText().equals(""))) {
                nShift2ErrorText.setText("Work Hours Second Half should be empty or filled!");
                nShift2ErrorText.setVisible(true);
                check = false;
            } else if (isInvalidTime(nSecondHalfStartTf.getText()) || isInvalidTime(nSecondHalfEndTf.getText())) {
                nShift2ErrorText.setText("Work Hours Second Half should be in valid 24-hour time format!");
                nShift2ErrorText.setVisible(true);
                check = false;
            } else if (!nSecondHalfStartTf.getText().equals("") && !nSecondHalfEndTf.getText().equals("")) {
                int start = Integer.parseInt(nSecondHalfStartTf.getText());
                int end = Integer.parseInt(nSecondHalfEndTf.getText());
                if (start >= end) {
                    nShift2ErrorText.setText("Start time should be before end time!");
                    nShift2ErrorText.setVisible(true);
                    check = false;
                }
            }

            if (isInvalidTime(nOvertimeStartTf.getText())) {
                nOvertimeStartErrorText.setText("Overtime Start Time should be in valid 24-hour time format!");
                nOvertimeStartErrorText.setVisible(true);
                check = false;
            }
            if (isInvalidTime(nOvertimeEndTf.getText())) {
                nOvertimeEndErrorText.setText("Overtime End Time should be in valid 24-hour time format!");
                nOvertimeEndErrorText.setVisible(true);
                check = false;
            }
            if (!check) {
                return;
            }

            int timeIn1, timeOut1, timeIn2 = 0, timeOut2 = 0, overtimeIn = 0, overtimeOut = 0;

            timeIn1 = Integer.parseInt(nFirstHalfStartTf.getText());
            int end = Integer.parseInt(nFirstHalfEndTf.getText());
            timeOut1 = end;
            if (!nSecondHalfStartTf.getText().equals("") && !nSecondHalfEndTf.getText().equals("")) {
                timeIn2 = Integer.parseInt(nSecondHalfStartTf.getText());
                timeOut2 = Integer.parseInt(nSecondHalfEndTf.getText());
                if (timeIn2 < end) {
                    nShift2ErrorText.setText("Work Hours Second Half should start after First Half!");
                    nShift2ErrorText.setVisible(true);
                    return;
                }
                end = timeOut2;
            }

            if (!nOvertimeStartTf.getText().equals("")) {
                overtimeIn = Integer.parseInt(nOvertimeStartTf.getText());
                if (overtimeIn > timeIn1) {
                    nOvertimeStartErrorText.setText("Overtime Start Time should be before Work Hours!");
                    nOvertimeStartErrorText.setVisible(true);
                    check = false;
                }
            }
            if (!nOvertimeEndTf.getText().equals("")) {
                overtimeOut = Integer.parseInt(nOvertimeEndTf.getText());
                if (overtimeOut < end) {
                    nOvertimeEndErrorText.setText("Overtime End Time should be after Work Hours!");
                    nOvertimeEndErrorText.setVisible(true);
                    check = false;
                }
            }

            if (check) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.setContentText("Save changes to approved overtime and work hours?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean acknowledged = model.addWorkday(date, timeIn1, timeOut1, timeIn2,
                            timeOut2, overtimeIn, overtimeOut);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setGraphic(null);

                    if (acknowledged) {
                        System.out.println("Workday added");
                        alert.setTitle("Success");
                        alert.setContentText("Changes to approved overtime and work hours for the Current day is saved");
                    } else {
                        System.out.println("Add Workday failed");
                        alert.setTitle("Error");
                        alert.setContentText("Failed to Save approved overtime and work hours");
                    }

                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    private void onCurrentEditAction() {
        hideCurrentErrorText();

        if (currentEditBtn.getText().equals("Edit")) { // on edit btn click
            currentEditBtn.setText("Save");
            currentCancelBtn.setVisible(true);
            setCurrentTextFieldsStatus(false);
        } else if (currentEditBtn.getText().equals("Save")) {
            boolean check = true;
            if (cFirstHalfStartTf.getText().equals("") || cFirstHalfEndTf.getText().equals("")) {
                cShift1ErrorText.setText("Work Hours First Half should be filled!");
                cShift1ErrorText.setVisible(true);
                check = false;
            } else if (isInvalidTime(cFirstHalfStartTf.getText()) || isInvalidTime(cFirstHalfEndTf.getText())) {
                cShift1ErrorText.setText("Work Hours First Half should be in valid 24-hour time format!");
                cShift1ErrorText.setVisible(true);
                check = false;
            } else {
                int start = Integer.parseInt(cFirstHalfStartTf.getText());
                int end = Integer.parseInt(cFirstHalfEndTf.getText());
                if (start >= end) {
                    cShift1ErrorText.setText("Start time should be before end time!");
                    cShift1ErrorText.setVisible(true);
                    check = false;
                }
            }

            if ((cSecondHalfStartTf.getText().equals("") && !cSecondHalfEndTf.getText().equals("")) ||
                    (!cSecondHalfStartTf.getText().equals("") && cSecondHalfEndTf.getText().equals(""))) {
                cShift2ErrorText.setText("Work Hours Second Half should be empty or filled!");
                cShift2ErrorText.setVisible(true);
                check = false;
            } else if (isInvalidTime(cSecondHalfStartTf.getText()) || isInvalidTime(cSecondHalfEndTf.getText())) {
                cShift2ErrorText.setText("Work Hours Second Half should be in valid 24-hour time format!");
                cShift2ErrorText.setVisible(true);
                check = false;
            } else if (!cSecondHalfStartTf.getText().equals("") && !cSecondHalfEndTf.getText().equals("")) {
                int start = Integer.parseInt(cSecondHalfStartTf.getText());
                int end = Integer.parseInt(cSecondHalfEndTf.getText());
                if (start >= end) {
                    cShift2ErrorText.setText("Start time should be before end time!");
                    cShift2ErrorText.setVisible(true);
                    check = false;
                }
            }

            if (isInvalidTime(cOvertimeStartTf.getText())) {
                cOvertimeStartErrorText.setText("Overtime Start Time should be in valid 24-hour time format!");
                cOvertimeStartErrorText.setVisible(true);
                check = false;
            }
            if (isInvalidTime(cOvertimeEndTf.getText())) {
                cOvertimeEndErrorText.setText("Overtime End Time should be in valid 24-hour time format!");
                cOvertimeEndErrorText.setVisible(true);
                check = false;
            }
            if (!check) {
                return;
            }

            int timeIn1, timeOut1, timeIn2 = 0, timeOut2 = 0, overtimeIn = 0, overtimeOut = 0;

            timeIn1 = Integer.parseInt(cFirstHalfStartTf.getText());
            int end = Integer.parseInt(cFirstHalfEndTf.getText());
            timeOut1 = end;
            if (!cSecondHalfStartTf.getText().equals("") && !cSecondHalfEndTf.getText().equals("")) {
                timeIn2 = Integer.parseInt(cSecondHalfStartTf.getText());
                timeOut2 = Integer.parseInt(cSecondHalfEndTf.getText());
                if (timeIn2 < end) {
                    cShift2ErrorText.setText("Work Hours Second Half should start after First Half!");
                    cShift2ErrorText.setVisible(true);
                    return;
                }
                end = timeOut2;
            }

            if (!cOvertimeStartTf.getText().equals("")) {
                overtimeIn = Integer.parseInt(cOvertimeStartTf.getText());
                if (overtimeIn > timeIn1) {
                    cOvertimeStartErrorText.setText("Overtime Start Time should be before Work Hours!");
                    cOvertimeStartErrorText.setVisible(true);
                    check = false;
                }
            }
            if (!cOvertimeEndTf.getText().equals("")) {
                overtimeOut = Integer.parseInt(cOvertimeEndTf.getText());
                if (overtimeOut < end) {
                    cOvertimeEndErrorText.setText("Overtime End Time should be after Work Hours!");
                    cOvertimeEndErrorText.setVisible(true);
                    check = false;
                }
            }

            if (check) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.setContentText("Save changes to approved overtime and work hours?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean acknowledged = model.addWorkday(date, timeIn1, timeOut1, timeIn2,
                            timeOut2, overtimeIn, overtimeOut);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setGraphic(null);

                    if (acknowledged) {
                        System.out.println("Workday added");
                        alert.setTitle("Success");
                        alert.setContentText("Changes to approved overtime and work hours for the Current day is saved");
                    } else {
                        System.out.println("Add Workday failed");
                        alert.setTitle("Error");
                        alert.setContentText("Failed to Save approved overtime and work hours");
                    }

                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    private void onNextCancelAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Cancel changes to approved overtime and work hours?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            setNextTextFieldsStatus(true);
            hideNextErrorText();
            nextCancelBtn.setVisible(false);

        }
    }

    @FXML
    private void onCurrentCancelAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Cancel changes to approved overtime and work hours?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            setCurrentTextFieldsStatus(true);
            hideCurrentErrorText();
            currentCancelBtn.setVisible(false);
        }
    }
}
