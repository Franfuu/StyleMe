package com.github.Franfuu.view;

import com.github.Franfuu.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController extends Controller implements Initializable {

    @FXML
    private BorderPane borderPane;
    private Controller centerController;

    @Override
    public void onOpen(Object input) throws Exception {
        // Cambia la escena a la escena de bienvenida al abrir
        changeScene(Scenes.WELCOME, null);
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Método llamado automáticamente después de que se cargue el archivo FXML
    }

    public static View loadFXML(Scenes scenes) throws Exception {
        // Carga el archivo FXML correspondiente a la escena
        String url = scenes.getURL();
        System.out.println(url);
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene = p;
        view.controller = c;
        return view;
    }

    public void changeScene(Scenes scene, Object data) throws Exception {
        // Cambia la escena en el BorderPane central
        View view = loadFXML(scene);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
    }

    public void openModal(Scenes scene, String title, Controller parent, Object data) throws Exception {
        // Abre una nueva ventana modal con la escena especificada
        View view = loadFXML(scene);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(parent);
        stage.showAndWait();
    }

    @Override
    public void onClose(Object output) {
        // Método llamado cuando se cierra la vista
    }
}