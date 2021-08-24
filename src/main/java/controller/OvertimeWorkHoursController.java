package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.PayrollEntry;

import java.io.IOException;
import java.util.Optional;

public class OvertimeWorkHoursController extends Controller{
    @FXML
    private AnchorPane navBar_container;

    /**
     * initialization of Overtime Table related objects
     */
    @FXML
    private TableColumn nameTc, overtimeTc, dateTc, buttonTc;

    @FXML
    private TableView overtimeTv;

    /**
     * initialization of work hours and overtime related objects
     */
    @FXML
    private Button workEditBtn, workCancelBtn, workSaveBtn, overtimeEditBtn, overtimeCancelBtn, overtimeSaveBtn,
            acceptBtn, rejectBtn, checkAllBtn;
    @FXML
    private TextField overtimeStartTf, overtimeEndTf, workStartTf, workEndTf, workStartTf2, workEndTf2;

    @Override
    public void update() {

    }

    @FXML
    public void initialize(){

        //load navigation bar
        FXMLLoader loader = new FXMLLoader();
        try {
            Node node  =  loader.load(getClass().getResource("/fxml/navBar.fxml").openStream());
            navBar_container.getChildren().add(node);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //setColumnWidth();
        disableReorder();
    }

    /**
     * This method sets the size of table columns present in OvertimeWorkHours.fxml
     */

    private void setColumnWidth(){
        nameTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        overtimeTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        dateTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        buttonTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
    }



    /**
     * This method simply disables reorderability for all table columns in Payroll.fxml
     */
    private void disableReorder() {
        nameTc.setReorderable(false);
        overtimeTc.setReorderable(false);
        dateTc.setReorderable(false);
        buttonTc.setReorderable(false);
    }

}
