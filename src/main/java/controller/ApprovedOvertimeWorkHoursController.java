package controller;

import dao.WorkdayPOJO;
import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.WorkdayHandler;
import wrapper.WorkdayWrapper;

import java.util.Date;
import java.util.Optional;

public class ApprovedOvertimeWorkHoursController extends Controller{
    @FXML
    private AnchorPane navBar_container;

    @FXML
    private Button nextEditBtn, currentEditBtn, currentSaveBtn, nextCancelBtn, currentCancelBtn, nextSaveBtn;

    /**
     * initialization of error text  and textfields in EmployeeForm.fxml
     */
    @FXML
    private Text cDateText, nDateText, cOvertimeStartErrorText,cShift1ErrorText, cShift2ErrorText,
            cOvertimeEndErrorText, nOvertimeStartErrorText,nShift1ErrorText, nShift2ErrorText, nOvertimeEndErrorText;
    @FXML
    private TextField cOvertimeStartTf, cOvertimeEndTf, nOvertimeStartTf, nOvertimeEndTf;
    @FXML
    private TextField cFirstHalfStartTf, cFirstHalfEndTf, nFirstHalfStartTf, nFirstHalfEndTf;
    @FXML
    private TextField cSecondHalfStartTf, cSecondHalfEndTf, nSecondHalfStartTf, nSecondHalfEndTf;

    private WorkdayHandler model;
    private WorkdayWrapper currentWorkday, nextWorkday;

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        hideCurrentErrorText();
        setCurrentTextFieldsStatus(true);
        currentEditBtn.setText("Edit");
        currentCancelBtn.setVisible(false);

        hideNextErrorText();
        setNextTextFieldsStatus(true);
        nextEditBtn.setText("Edit");
        nextCancelBtn.setVisible(false);

        long ms = new Date().getTime();
        model = new WorkdayHandler(new Date(ms), new Date(ms + 86400000L));
        currentWorkday = model.getCurrentWorkday();
        nextWorkday = model.getNextWorkday();
        cDateText.setText(currentWorkday.getDateString());
        nDateText.setText(nextWorkday.getDateString());

        resetCurrentWorkday();
        resetNextWorkday();
    }

    @FXML
    public void initialize() {
        hideCurrentErrorText();
        setCurrentTextFieldsStatus(true);
        currentEditBtn.setText("Edit");
        currentCancelBtn.setVisible(false);

        hideNextErrorText();
        setNextTextFieldsStatus(true);
        nextEditBtn.setText("Edit");
        nextCancelBtn.setVisible(false);

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
    }

    private void initTextField(TextField tf) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d*")) {
                tf.setText(oldValue);
            }
        });
    }

    private void resetCurrentWorkday() {
        cFirstHalfStartTf.setText(currentWorkday.getTimeIn1String());
        cFirstHalfEndTf.setText(currentWorkday.getTimeOut1String());
        cSecondHalfStartTf.setText(currentWorkday.getTimeIn2String());
        cSecondHalfEndTf.setText(currentWorkday.getTimeOut2String());
        cOvertimeStartTf.setText(currentWorkday.getOvertimeInString());
        cOvertimeEndTf.setText(currentWorkday.getOvertimeOutString());
    }

    private void resetNextWorkday() {
        nFirstHalfStartTf.setText(nextWorkday.getTimeIn1String());
        nFirstHalfEndTf.setText(nextWorkday.getTimeOut1String());
        nSecondHalfStartTf.setText(nextWorkday.getTimeIn2String());
        nSecondHalfEndTf.setText(nextWorkday.getTimeOut2String());
        nOvertimeStartTf.setText(nextWorkday.getOvertimeInString());
        nOvertimeEndTf.setText(nextWorkday.getOvertimeOutString());
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

    private WorkdayPOJO onSaveAction(Text shift1ErrorText, Text shift2ErrorText,
                                     Text overtimeStartErrorText, Text overtimeEndErrorText,
                                     TextField firstHalfStartTf, TextField firstHalfEndTf,
                                     TextField secondHalfStartTf, TextField secondHalfEndTf,
                                     TextField overtimeStartTf, TextField overtimeEndTf) {
        boolean check = true;
        if (firstHalfStartTf.getText().equals("") || firstHalfEndTf.getText().equals("")) {
            shift1ErrorText.setText("Work Hours First Half should be filled!");
            shift1ErrorText.setVisible(true);
            check = false;
        } else if (isInvalidTime(firstHalfStartTf.getText()) || isInvalidTime(firstHalfEndTf.getText())) {
            shift1ErrorText.setText("Work Hours First Half should be in valid 24-hour time format!");
            shift1ErrorText.setVisible(true);
            check = false;
        } else {
            int start = Integer.parseInt(firstHalfStartTf.getText());
            int end = Integer.parseInt(firstHalfEndTf.getText());
            if (start >= end) {
                shift1ErrorText.setText("Start time should be before end time!");
                shift1ErrorText.setVisible(true);
                check = false;
            }
        }

        if ((secondHalfStartTf.getText().equals("") && !secondHalfEndTf.getText().equals("")) ||
                (!secondHalfStartTf.getText().equals("") && secondHalfEndTf.getText().equals(""))) {
            shift2ErrorText.setText("Work Hours Second Half should be empty or filled!");
            shift2ErrorText.setVisible(true);
            check = false;
        } else if (isInvalidTime(secondHalfStartTf.getText()) || isInvalidTime(secondHalfEndTf.getText())) {
            shift2ErrorText.setText("Work Hours Second Half should be in valid 24-hour time format!");
            shift2ErrorText.setVisible(true);
            check = false;
        } else if (!secondHalfStartTf.getText().equals("") && !secondHalfEndTf.getText().equals("")) {
            int start = Integer.parseInt(secondHalfStartTf.getText());
            int end = Integer.parseInt(secondHalfEndTf.getText());
            if (start >= end) {
                shift2ErrorText.setText("Start time should be before end time!");
                shift2ErrorText.setVisible(true);
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
            return null;
        }

        int timeIn1, timeOut1, timeIn2 = 0, timeOut2 = 0, overtimeIn = 0, overtimeOut = 0;

        timeIn1 = Integer.parseInt(firstHalfStartTf.getText());
        int end = Integer.parseInt(firstHalfEndTf.getText());
        timeOut1 = end;
        if (!secondHalfStartTf.getText().equals("") && !secondHalfEndTf.getText().equals("")) {
            timeIn2 = Integer.parseInt(secondHalfStartTf.getText());
            timeOut2 = Integer.parseInt(secondHalfEndTf.getText());
            if (timeIn2 < end) {
                shift2ErrorText.setText("Work Hours Second Half should start after First Half!");
                shift2ErrorText.setVisible(true);
                return null;
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
        WorkdayPOJO workday = null;
        if (check) {
            workday = new WorkdayPOJO(nextWorkday.getDate(), timeIn1,
                    timeOut1, timeIn2, timeOut2, overtimeIn, overtimeOut);
        }
        return workday;
    }

    @FXML
    private void onCurrentEditAction() {
        hideCurrentErrorText();
        // on edit btn click
        currentSaveBtn.setVisible(true);
        currentCancelBtn.setVisible(true);
        setCurrentTextFieldsStatus(false);

    }

    @FXML
    private void onCurrentSaveAction(){
        WorkdayPOJO workday = onSaveAction(cShift1ErrorText, cShift2ErrorText,
                cOvertimeStartErrorText, cOvertimeEndErrorText, cFirstHalfStartTf,
                cFirstHalfEndTf, cSecondHalfStartTf, cSecondHalfEndTf,
                cOvertimeStartTf, cOvertimeEndTf);

        if (workday != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Save changes to approved overtime and work hours?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                model.updateCurrentWorkday(workday);
                currentWorkday = model.getCurrentWorkday();

                setCurrentTextFieldsStatus(true);
                currentSaveBtn.setVisible(false);
                currentCancelBtn.setVisible(false);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.setTitle("Success");
                alert.setContentText("Changes to approved overtime and work hours for the current day are saved!");

                System.out.println("Current workday updated");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onNextEditAction() {
        hideNextErrorText();
        // on edit btn click
        nextSaveBtn.setVisible(true);
        nextCancelBtn.setVisible(true);
        setNextTextFieldsStatus(false);
    }

    @FXML
    private void onNextSaveAction(){
        WorkdayPOJO workday = onSaveAction(nShift1ErrorText, nShift2ErrorText,
                nOvertimeStartErrorText, nOvertimeEndErrorText, nFirstHalfStartTf,
                nFirstHalfEndTf, nSecondHalfStartTf, nSecondHalfEndTf,
                nOvertimeStartTf, nOvertimeEndTf);

        if (workday != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Save changes to approved overtime and work hours?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                model.updateNextWorkday(workday);
                nextWorkday = model.getNextWorkday();

                setNextTextFieldsStatus(true);
                nextSaveBtn.setVisible(false);
                nextCancelBtn.setVisible(false);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.setTitle("Success");
                alert.setContentText("Changes to approved overtime and work hours for the next day are saved!");

                System.out.println("Next workday updated");
                alert.showAndWait();
            }
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
            currentSaveBtn.setVisible(false);

            resetCurrentWorkday();
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
            nextSaveBtn.setVisible(false);

            resetNextWorkday();
        }
    }
}
