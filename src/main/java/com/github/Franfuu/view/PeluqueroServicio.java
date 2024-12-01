package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.ServicioDAO;
import com.github.Franfuu.model.entity.Servicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class PeluqueroServicio extends Controller {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField precioField;

    @FXML
    private void onGuardarServicio() {
        String nombre = nombreField.getText();
        String precio = precioField.getText();

        if (nombre.isEmpty() || precio.isEmpty()) {
            showAlert("Error", "Todos los campos deben estar llenos.");
            return;
        }

        Servicio servicio = new Servicio();
        servicio.setNombre(nombre);
        servicio.setPrecio(precio);

        ServicioDAO servicioDAO = new ServicioDAO();
        servicioDAO.save(servicio);

        showAlert("Éxito", "Servicio guardado exitosamente.");
    }

    @FXML
    private void onVolver() {
        try {
            App.currentController.changeScene(Scenes.PELUCITA, null);
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

    @Override
    public void onOpen(Object input) throws Exception {
    }

    @Override
    public void onClose(Object output) {
    }
}