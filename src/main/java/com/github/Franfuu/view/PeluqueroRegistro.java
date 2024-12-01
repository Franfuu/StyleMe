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
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField specialityField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onRegister() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String speciality = specialityField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() || speciality.isEmpty() || password.isEmpty() || !isValidEmail(email) || phone.length() < 9 || password.length() < 8) {
            showAlert("Error", "No puede dejar ningun campo vacio, el correo debe ser valido, el telefono debe tener al menos 9 digitos y la contraseña debe tener al menos 8 caracteres.");
            return;
        }

        PeluqueroDAO peluqueroDAO = new PeluqueroDAO();
        if (peluqueroDAO.emailExists(email)) {
            showAlert("Error", "El correo ya está registrado.");
            return;
        }

        if (peluqueroDAO.phoneExists(phone)) {
            showAlert("Error", "El número de teléfono ya está registrado.");
            return;
        }

        Peluquero peluquero = new Peluquero();
        peluquero.setNombre(name);
        peluquero.setApellido(surname);
        peluquero.setCorreo(email);
        peluquero.setTelefono(phone);
        peluquero.setEspecialidad(speciality);
        peluquero.setContraseña(password);


        peluqueroDAO.save(peluquero);

        showAlert("Success", "Peluquero registered successfully.");

        try {
            App.currentController.changeScene(Scenes.INIPELU, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
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