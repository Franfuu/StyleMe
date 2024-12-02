package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.ServicioDAO;
import com.github.Franfuu.model.entity.Servicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class PeluqueroServicio extends Controller {

    @FXML
    private TextField nombreField; // Campo de texto para el nombre del servicio

    @FXML
    private TextField precioField; // Campo de texto para el precio del servicio

    @FXML
    private void onGuardarServicio() {
        // Método llamado al hacer clic en el botón de guardar servicio
        String nombre = nombreField.getText();
        String precio = precioField.getText();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || precio.isEmpty()) {
            showAlert("Error", "Todos los campos deben estar llenos.");
            return;
        }

        // Crear un nuevo servicio y guardarlo en la base de datos
        Servicio servicio = new Servicio();
        servicio.setNombre(nombre);
        servicio.setPrecio(precio);

        ServicioDAO servicioDAO = new ServicioDAO();
        servicioDAO.save(servicio);

        showAlert("Éxito", "Servicio guardado exitosamente.");
    }

    @FXML
    private void onVolver() {
        // Método llamado al hacer clic en el botón de volver
        try {
            App.currentController.changeScene(Scenes.PELUCITA, null);
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

    @Override
    public void onOpen(Object input) throws Exception {
        // Método llamado cuando se abre la vista
    }

    @Override
    public void onClose(Object output) {
        // Método llamado cuando se cierra la vista
    }
}