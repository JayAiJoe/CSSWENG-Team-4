package controller;

import driver.Driver;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.OvertimeEntry;
import wrapper.EmployeeWrapper;

import java.util.function.Predicate;

public class COLAController extends Controller{

    @FXML
    private AnchorPane navBar_container;

    @FXML
    private ChoiceBox companyCb, checkedCb;

    @FXML
    private Button checkAllBtn, searchBtn;

    @FXML
    private TextField nameTf;

    @FXML
    private TableColumn idTc, nameTc, companyTc, buttonTc;

    private FilteredList<EmployeeWrapper> filteredEntries;
    private Predicate<EmployeeWrapper> nameFilter = entry -> true, companyFilter = entry -> true,
            checkedFilter = entry -> true;

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }
    }

    @FXML
    public void initialize(){

    }

    @FXML
    private void onFilterNameAction() {
        nameFilter = entry -> entry.getCompleteName().toLowerCase()
                .contains(nameTf.getText().toLowerCase());
        filteredEntries.setPredicate(nameFilter.and(companyFilter));
    }

    public void onFilterCompanyAction(){
        /*
        if (companyCb.getValue().equals("All")) {
            companyFilter = entry -> true;
        } else {
            companyFilter = entry ->
                    entry.getCompanyFull().equals(companyCb.getValue());
        }
        filteredEntries.setPredicate(companyFilter.and(nameFilter));*/

    }

    public void onFilterCheckedAction(){
        /*
        if (checkedCb.getValue().equals("All")) {
            checkedFilter = entry -> true;
        } else {

        }
        filteredEntries.setPredicate(checkedFilter.and(nameFilter));*/

    }

    @FXML
    private void onCheckAllAction() {
        /*
        boolean value = checkAllBtn.getText().equals("Check All");
        for (OvertimeEntry entry : filteredEntries) {
            entry.setStatus(value);
        }
        if (value) {
            checkAllBtn.setText("Uncheck All");
        } else {
            checkAllBtn.setText("Check All");
        }
        employeeTv.refresh();*/
    }

}
