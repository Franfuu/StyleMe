package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.Cita_ServicioDAO;
import com.github.Franfuu.model.entity.Cita;
import com.github.Franfuu.model.entity.Servicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EliminarServicio extends Controller implements Initializable {

    @FXML
    private ComboBox<Servicio> servicioComboBox; // ComboBox para seleccionar un servicio

    private Cita selectedCita; // La cita seleccionada

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializar el controlador
    }

    @Override
    public void onOpen(Object input) throws Exception {
        // Método llamado cuando se abre la vista
        if (input instanceof Cita) {
            this.selectedCita = (Cita) input;
            loadServicios();
        }
    }

    private void loadServicios() {
        // Cargar los servicios asociados con la cita seleccionada
        Cita_ServicioDAO citaServicioDAO = new Cita_ServicioDAO();
        List<Servicio> servicios = citaServicioDAO.findServiciosByCita(selectedCita.getId());
        ObservableList<Servicio> servicioList = FXCollections.observableArrayList(servicios);
        servicioComboBox.setItems(servicioList);
    }

    @FXML
    private void onEliminarServicio() {
        // Método llamado cuando se hace clic en el botón "Eliminar Servicio"
        Servicio selectedServicio = servicioComboBox.getSelectionModel().getSelectedItem();
        if (selectedServicio == null) {
            showAlert("Error", "Debe seleccionar un servicio.");
            return;
        }

        Cita_ServicioDAO citaServicioDAO = new Cita_ServicioDAO();
        try {
            citaServicioDAO.deleteServicioEnCita(selectedCita.getId(), selectedServicio.getId());
            showAlert("Éxito", "Servicio eliminado exitosamente.");
            loadServicios();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error al eliminar el servicio.");
        }
    }

    @FXML
    private void onVolver() {
        // Método llamado cuando se hace clic en el botón "Volver"
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
    public void onClose(Object output) {
        // Método llamado cuando se cierra la vista
    }
}