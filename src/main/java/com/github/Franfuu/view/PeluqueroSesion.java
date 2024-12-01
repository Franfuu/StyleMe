package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.PeluqueroDAO;
import com.github.Franfuu.model.entity.ControlSesion;
import com.github.Franfuu.model.entity.Peluquero;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PeluqueroSesion extends Controller implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @Override
    public void onOpen(Object input) throws Exception {
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void onLogin() throws Exception {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "No puedes dejar campos vacíos.");
            return;
        }

        PeluqueroDAO peluqueroDAO = new PeluqueroDAO();
        Peluquero peluquero = peluqueroDAO.findByEmailAndPassword(email, password);

        if (peluquero != null) {
            ControlSesion.getInstance().setLoggedInClienteId(peluquero.getId());
            showAlert("Sesión Iniciada", "Sesión iniciada correctamente.");
            App.currentController.changeScene(Scenes.PELUCITA, null);
        } else {
            showAlert("Error", "Credenciales incorrectas.");
        }
    }

    @FXML
    private void onRegister() {
        try {
            App.currentController.changeScene(Scenes.REGPELU, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBack() {
        try {
            App.currentController.changeScene(Scenes.ELEGIROL, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}