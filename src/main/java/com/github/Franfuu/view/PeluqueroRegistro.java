package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.ClienteDAO;
import com.github.Franfuu.model.dao.PeluqueroDAO;
import com.github.Franfuu.model.entity.Peluquero;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PeluqueroRegistro extends Controller implements Initializable {

    @FXML
    private TextField nameField; // Campo de texto para el nombre

    @FXML
    private TextField surnameField; // Campo de texto para el apellido

    @FXML
    private TextField emailField; // Campo de texto para el correo electrónico

    @FXML
    private TextField phoneField; // Campo de texto para el teléfono

    @FXML
    private TextField specialityField; // Campo de texto para la especialidad

    @FXML
    private PasswordField passwordField; // Campo de texto para la contraseña

    @FXML
    private void onRegister() {
        // Método llamado al hacer clic en el botón de registro
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String speciality = specialityField.getText();
        String password = passwordField.getText();

        // Validar los campos de entrada
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() || speciality.isEmpty() || password.isEmpty() || !isValidEmail(email) || phone.length() < 9 || password.length() < 8) {
            showAlert("Error", "No puede dejar ningun campo vacio, el correo debe ser valido, el telefono debe tener al menos 9 digitos y la contraseña debe tener al menos 8 caracteres.");
            return;
        }

        PeluqueroDAO peluqueroDAO = new PeluqueroDAO();
        // Verificar si el correo ya está registrado
        if (peluqueroDAO.emailExists(email)) {
            showAlert("Error", "El correo ya está registrado.");
            return;
        }

        // Verificar si el número de teléfono ya está registrado
        if (peluqueroDAO.phoneExists(phone)) {
            showAlert("Error", "El número de teléfono ya está registrado.");
            return;
        }

        // Crear un nuevo peluquero y guardar en la base de datos
        Peluquero peluquero = new Peluquero();
        peluquero.setNombre(name);
        peluquero.setApellido(surname);
        peluquero.setCorreo(email);
        peluquero.setTelefono(phone);
        peluquero.setEspecialidad(speciality);
        peluquero.setContraseña(password);

        peluqueroDAO.save(peluquero);

        showAlert("Success", "Peluquero registrado exitosamente.");

        // Cambiar a la escena de inicio de peluquero
        try {
            App.currentController.changeScene(Scenes.INIPELU, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLogin() {
        // Método llamado al hacer clic en el botón de inicio de sesión
        try {
            App.currentController.changeScene(Scenes.INICLI, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBack() {
        // Método llamado al hacer clic en el botón de volver
        try {
            App.currentController.changeScene(Scenes.WELCOME, null);
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

    private boolean isValidEmail(String email) {
        // Validar el formato del correo electrónico
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

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
}