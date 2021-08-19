package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class ScreenController {
    private HashMap<String, FXMLLoader> loaderMap = new HashMap<>();
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    public void addScreen(String name, FXMLLoader loader) throws IOException {
        loaderMap.put(name, loader);
        screenMap.put(name, loader.load());
    }

    public void removeScreen(String name){
        loaderMap.remove(name);
        screenMap.remove(name);
    }

    public void activate(String name) {
        FXMLLoader loader = loaderMap.get(name);
        Controller controller = loader.getController();
        controller.update();
        main.setRoot(screenMap.get(name));
    }
}
