package controller;

import dao.EmployeePOJO;
import dao.PerformancePOJO;
import driver.Driver;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.EmployeeForm;

import java.io.IOException;

public class EmployeesController extends Controller{
    private static final PseudoClass COLUMN_HOVER_PSEUDO_CLASS = PseudoClass.getPseudoClass("column-hover");

    @FXML
    private AnchorPane navBar_container;

    @FXML
    private ChoiceBox companyCb;

    @FXML
    private TableView<EmployeePOJO> employeesTv;

    @FXML
    private TableColumn<EmployeePOJO, String> nameTc, frequencyTc, modeTc, companyTc, wageTc, buttonTc, idTc;

    private EmployeeForm employeeForm;
    private ObservableList<EmployeePOJO> entries = FXCollections.observableArrayList();

    @Override
    public void update() {

        disableReorder();

        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        employeeForm = new EmployeeForm();
        entries.setAll(employeeForm.getEmployees());
        employeesTv.setItems(entries);

    }

    @FXML
    public void initialize() {
        initCol(idTc, "employeeID");
        initCol(nameTc, "completeName");
        initCol(frequencyTc, "wageFrequency");
        initCol(modeTc, "mode");
        initCol(companyTc, "company");
        initCol(wageTc, "wage");
    }

    private <T> void initCol(TableColumn<EmployeePOJO, T> col, String tag) {
        col.setCellValueFactory(new PropertyValueFactory<>(tag));

        // hover property
        BooleanProperty columnHover = new SimpleBooleanProperty();

        col.setCellFactory(column -> {
            TableCell<EmployeePOJO, T> cell = new TableCell<>();

            if (!tag.isEmpty()) {
                cell.textProperty().bind(Bindings.createStringBinding(() -> cell.isEmpty() ? "" : String.format("%s", cell.getItem())
                        , cell.itemProperty(), cell.emptyProperty()));
            }
            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> columnHover.set(isNowHovered));

            columnHover.addListener((obs, columnWasHovered, columnIsNowHovered) ->
                    cell.pseudoClassStateChanged(COLUMN_HOVER_PSEUDO_CLASS, columnIsNowHovered)
            );
            return cell;
        });
    }

    private void disableReorder() {
        nameTc.setReorderable(false);
        companyTc.setReorderable(false);
        frequencyTc.setReorderable(false);
        buttonTc.setReorderable(false);
        modeTc.setReorderable(false);
        wageTc.setReorderable(false);
    }

    public void onAddAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmployeeForm.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setOpacity(1);
        stage.setScene(new Scene(root, 481, 448));
        stage.setResizable(false);
        stage.showAndWait();
    }




}
