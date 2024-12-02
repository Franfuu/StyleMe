package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.ServicioDAO;
import com.github.Franfuu.model.entity.Servicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditarEliminarServicio extends Controller implements Initializable {

    @FXML
    private ComboBox<Servicio> servicioComboBox; // ComboBox to select a service
    @FXML
    private TextField nombreField; // TextField to input the service name
    @FXML
    private TextField precioField; // TextField to input the service price

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the controller and load services into the ComboBox
        loadServicios();
        servicioComboBox.setOnAction(event -> loadServicioDetails());
    }

    private void loadServicios() {
        // Load all services from the database and set them in the ComboBox
        ServicioDAO servicioDAO = new ServicioDAO();
        List<Servicio> servicios = servicioDAO.findAll();
        ObservableList<Servicio> servicioList = FXCollections.observableArrayList(servicios);
        servicioComboBox.setItems(servicioList);
    }

    private void loadServicioDetails() {
        // Load the details of the selected service into the TextFields
        Servicio selectedServicio = servicioComboBox.getSelectionModel().getSelectedItem();
        if (selectedServicio != null) {
            nombreField.setText(selectedServicio.getNombre());
            precioField.setText(selectedServicio.getPrecio());
        }
    }

    @FXML
    private void onEditarServicio() {
        // Edit the selected service with the new details from the TextFields
        Servicio selectedServicio = servicioComboBox.getSelectionModel().getSelectedItem();
        if (selectedServicio == null) {
            showAlert("Error", "Debe seleccionar un servicio.");
            return;
        }

        String nombre = nombreField.getText();
        String precio = precioField.getText();

        if (nombre.isEmpty() || precio.isEmpty()) {
            showAlert("Error", "Todos los campos deben estar llenos.");
            return;
        }

        selectedServicio.setNombre(nombre);
        selectedServicio.setPrecio(precio);

        ServicioDAO servicioDAO = new ServicioDAO();
        servicioDAO.update(selectedServicio);

        showAlert("Éxito", "Servicio editado exitosamente.");
    }

    @FXML
    private void onEliminarServicio() {
        // Delete the selected service from the database
        Servicio selectedServicio = servicioComboBox.getSelectionModel().getSelectedItem();
        if (selectedServicio == null) {
            showAlert("Error", "Debe seleccionar un servicio.");
            return;
        }

        ServicioDAO servicioDAO = new ServicioDAO();
        servicioDAO.delete(selectedServicio.getId());

        showAlert("Éxito", "Servicio eliminado exitosamente.");
        loadServicios();
    }

    @FXML
    private void onVolver() {
        // Go back to the previous scene
        try {
            App.currentController.changeScene(Scenes.PELUCITA, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        // Show an alert with the specified title and message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void onOpen(Object input) throws Exception {
        // Method called when the view is opened
    }

    @Override
    public void onClose(Object output) {
        // Method called when the view is closed
    }
}