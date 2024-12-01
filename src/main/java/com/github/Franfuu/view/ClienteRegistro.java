package com.github.Franfuu.view;

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

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || password.isEmpty()) {
            showAlert("Error", "All fields must be filled out.");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(name);
        cliente.setApellido(surname);
        cliente.setCorreo(email);
        cliente.setTelefono(phone);
        cliente.setGenero(gender);
        cliente.setContraseña(password);

        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.save(cliente);

        showAlert("Success", "Client registered successfully.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}