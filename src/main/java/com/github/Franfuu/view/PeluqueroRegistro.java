package com.github.Franfuu.view;

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

    @FXML
    private void onRegister() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String gender = genderField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Completa todos lo campos.");
            return;
        }

        Peluquero peluquero = new Peluquero();
        peluquero.setNombre(name);
        peluquero.setApellido(surname);
        peluquero.setCorreo(email);
        peluquero.setTelefono(phone);
        peluquero.setEspecialidad(gender);
        peluquero.setContraseña(password);

        PeluqueroDAO peluqueroDAO = new PeluqueroDAO();
        peluqueroDAO.save(peluquero);

        showAlert("Success", "Peluquero registered successfully.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void onOpen(Object input) throws Exception {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}