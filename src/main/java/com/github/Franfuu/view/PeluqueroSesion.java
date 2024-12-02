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
    private TextField emailField; // Campo de texto para el correo electrónico

    @FXML
    private PasswordField passwordField; // Campo de texto para la contraseña

    @Override
    public void onOpen(Object input) throws Exception {
        // Método llamado cuando se abre la vista
    }

    @Override
    public void onClose(Object output) {
        // Método llamado cuando se cierra la vista
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializar el controlador
    }

    @FXML
    private void onLogin() throws Exception {
        // Método llamado al hacer clic en el botón de inicio de sesión
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validar que los campos no estén vacíos
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "No puedes dejar campos vacíos.");
            return;
        }

        PeluqueroDAO peluqueroDAO = new PeluqueroDAO();
        Peluquero peluquero = peluqueroDAO.findByEmailAndPassword(email, password);

        // Verificar las credenciales del peluquero
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
        // Método llamado al hacer clic en el botón de registro
        try {
            App.currentController.changeScene(Scenes.REGPELU, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBack() {
        // Método llamado al hacer clic en el botón de volver
        try {
            App.currentController.changeScene(Scenes.ELEGIROL, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        // Mostrar una alerta con el título y mensaje especificados
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}