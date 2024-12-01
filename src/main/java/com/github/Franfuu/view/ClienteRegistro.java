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
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField genderField;

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
    private void onRegister() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String gender = genderField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || password.isEmpty() || !isValidEmail(email) || phone.length() < 9 || password.length() < 8) {
            showAlert("Error", "No puede dejar ningun campo vacio, el correo debe ser valido, el telefono debe tener al menos 9 digitos y la contraseña debe tener al menos 8 caracteres.");
            return;
        }

        ClienteDAO clienteDAO = new ClienteDAO();
        if (clienteDAO.emailExists(email)) {
            showAlert("Error", "El correo ya está registrado.");
            return;
        }

        if (clienteDAO.phoneExists(phone)) {
            showAlert("Error", "El número de teléfono ya está registrado.");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(name);
        cliente.setApellido(surname);
        cliente.setCorreo(email);
        cliente.setTelefono(phone);
        cliente.setGenero(gender);
        cliente.setContraseña(password);


        clienteDAO.save(cliente);

        showAlert("Cliente Registrado", "Cliente registrado con exito.");

        try {
            App.currentController.changeScene(Scenes.INICLI, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    @FXML
    private void onLogin() {
        try {
            App.currentController.changeScene(Scenes.INICLI, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBack() {
        try {
            App.currentController.changeScene(Scenes.WELCOME, null);
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