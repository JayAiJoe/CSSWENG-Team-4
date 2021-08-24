package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomePageController extends Controller {

    @FXML
    private AnchorPane navBar_container;

    @Override
    public void update() {

    }

    @FXML
    public void initialize(){

        FXMLLoader loader = new FXMLLoader();
        try {
            Node node  =  loader.load(getClass().getResource("/fxml/navBar.fxml").openStream());
            navBar_container.getChildren().add(node);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }




}
