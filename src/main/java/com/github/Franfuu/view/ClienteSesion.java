package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.ClienteDAO;
import com.github.Franfuu.model.entity.Cliente;
import com.github.Franfuu.model.entity.ControlSesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClienteSesion extends Controller implements Initializable {

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
    private void onLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "No puedes dejar campos vacíos.");
            return;
        }

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.findByEmailAndPassword(email, password);

        if (cliente != null) {

            ControlSesion.getInstance().setLoggedInClienteId(cliente.getId());

            showAlert("Sesión Iniciada", "Sesión iniciada correctamente.");
            try {
                App.currentController.changeScene(Scenes.CLIENTELISTA, cliente.getNombre());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Credenciales incorrectas.");
        }
    }

    @FXML
    private void onRegister() {
        try {
            App.currentController.changeScene(Scenes.REGCLI, null);
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