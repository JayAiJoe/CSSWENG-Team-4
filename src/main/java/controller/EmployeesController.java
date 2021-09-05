package controller;

import dao.EmployeePOJO;
import driver.Driver;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.EmployeeForm;

import java.io.IOException;
import java.util.function.Predicate;

public class EmployeesController extends Controller{
    private static final PseudoClass COLUMN_HOVER_PSEUDO_CLASS = PseudoClass.getPseudoClass("column-hover");

    @FXML
    private AnchorPane navBar_container;

    @FXML
    private ChoiceBox companyCb;
    @FXML
    private TextField nameTf;

    @FXML
    private TableView<EmployeePOJO> employeesTv;
    @FXML
    private TableColumn<EmployeePOJO, String> nameTc, frequencyTc, modeTc, companyTc, wageTc, idTc;
    @FXML
    private TableColumn<EmployeePOJO, Button> buttonTc;

    private EmployeeForm employeeForm;
    private FilteredList<EmployeePOJO> filteredEntries;
    private Predicate<EmployeePOJO> nameFilter = entry -> true, companyFilter = entry -> true;

    @Override
    public void update() {
        disableReorder();

        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        employeeForm = new EmployeeForm();
        ObservableList<EmployeePOJO> entries = FXCollections.observableArrayList();
        entries.setAll(employeeForm.getEmployees());
        filteredEntries = new FilteredList<>(entries);
        employeesTv.setItems(filteredEntries);
    }

    @FXML
    public void initialize() {
        initCol(idTc, "employeeID");
        initCol(nameTc, "completeName");
        initCol(frequencyTc, "wageFrequency");
        initCol(modeTc, "mode");
        initCol(companyTc, "companyFull");
        initCol(wageTc, "wageString");
        buttonTc.setCellFactory(t -> {
            TableCell<EmployeePOJO, Button> cell = new TableCell<>() {
                private final Button editButton = new Button();
                @Override
                public void updateItem(Button item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        editButton.setText("Edit");
                        editButton.setOnMouseClicked(e -> onEditAction());
                        setGraphic(editButton);
                    }
                }
            };

            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
    }

    private <T> void initCol(TableColumn<EmployeePOJO, T> col, String tag) {
        col.setCellValueFactory(new PropertyValueFactory<>(tag));

        // hover property
        BooleanProperty columnHover = new SimpleBooleanProperty();

        col.setCellFactory(column -> {
            TableCell<EmployeePOJO, T> cell = new TableCell<>();

            if (!tag.isEmpty()) {
                cell.textProperty().bind(Bindings.createStringBinding(() -> cell.isEmpty() ? "" : String.format("%s", cell.getItem()),
                        cell.itemProperty(), cell.emptyProperty()));
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

    @FXML
    private void onAddAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmployeeForm.fxml"));
        Parent root = fxmlLoader.load();
        EmployeeFormController controller = fxmlLoader.getController();
        controller.setEmployeeForm(employeeForm);

        Stage stage = new Stage();
        stage.setOpacity(1);
        stage.setScene(new Scene(root, 481, 448));
        stage.setResizable(false);
        stage.showAndWait();

        ObservableList<EmployeePOJO> entries = FXCollections.observableArrayList();
        entries.setAll(employeeForm.getEmployees());
        filteredEntries = new FilteredList<>(entries);
        employeesTv.setItems(filteredEntries);
    }

    @FXML
    private void onFilterNameAction() {
        nameFilter = entry -> entry.getCompleteName().toLowerCase()
                .contains(nameTf.getText().toLowerCase());
        filteredEntries.setPredicate(nameFilter.and(companyFilter));
    }

    @FXML
    private void onFilterCompanyAction() {
        if (companyCb.getValue().equals("All")) {
            companyFilter = entry -> true;
        } else {
            companyFilter = entry ->
                    entry.getCompanyFull().equals(companyCb.getValue());
        }
        filteredEntries.setPredicate(companyFilter.and(nameFilter));
    }

    private void onEditAction() {
        // TODO: change screen
        System.out.println("edit button clicked");
    }
}