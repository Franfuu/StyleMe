package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.ClienteDAO;
import com.github.Franfuu.model.entity.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClienteRegistro extends Controller implements Initializable {

    @FXML
    private TextField nameField; // Campo de texto para el nombre

    @FXML
    private TextField surnameField; // Campo de texto para el apellido

    @FXML
    private TextField emailField; // Campo de texto para el correo electrónico

    @FXML
    private TextField phoneField; // Campo de texto para el teléfono

    @FXML
    private TextField genderField; // Campo de texto para el género

    @FXML
    private PasswordField passwordField; // Campo de texto para la contraseña

    @Override
    public void onOpen(Object input) throws Exception {
        // Método llamado al abrir la vista
    }

    @Override
    public void onClose(Object output) {
        // Método llamado al cerrar la vista
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Método llamado automáticamente después de cargar el archivo FXML
    }

    @FXML
    private void onRegister() {
        // Método llamado al hacer clic en el botón de registro
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String gender = genderField.getText();
        String password = passwordField.getText();

        // Validar los campos de entrada
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || password.isEmpty() || !isValidEmail(email) || phone.length() < 9 || password.length() < 8) {
            showAlert("Error", "No puede dejar ningun campo vacio, el correo debe ser valido, el telefono debe tener al menos 9 digitos y la contraseña debe tener al menos 8 caracteres.");
            return;
        }

        ClienteDAO clienteDAO = new ClienteDAO();
        // Verificar si el correo ya está registrado
        if (clienteDAO.emailExists(email)) {
            showAlert("Error", "El correo ya está registrado.");
            return;
        }

        // Verificar si el número de teléfono ya está registrado
        if (clienteDAO.phoneExists(phone)) {
            showAlert("Error", "El número de teléfono ya está registrado.");
            return;
        }

        // Crear un nuevo cliente y guardar en la base de datos
        Cliente cliente = new Cliente();
        cliente.setNombre(name);
        cliente.setApellido(surname);
        cliente.setCorreo(email);
        cliente.setTelefono(phone);
        cliente.setGenero(gender);
        cliente.setContraseña(password);

        clienteDAO.save(cliente);

        showAlert("Cliente Registrado", "Cliente registrado con exito.");

        // Cambiar a la escena de inicio de sesión
        try {
            App.currentController.changeScene(Scenes.INICLI, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        // Validar el formato del correo electrónico
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
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
}