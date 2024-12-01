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
    private ComboBox<Servicio> servicioComboBox;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField precioField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadServicios();
        servicioComboBox.setOnAction(event -> loadServicioDetails());
    }

    private void loadServicios() {
        ServicioDAO servicioDAO = new ServicioDAO();
        List<Servicio> servicios = servicioDAO.findAll();
        ObservableList<Servicio> servicioList = FXCollections.observableArrayList(servicios);
        servicioComboBox.setItems(servicioList);
    }

    private void loadServicioDetails() {
        Servicio selectedServicio = servicioComboBox.getSelectionModel().getSelectedItem();
        if (selectedServicio != null) {
            nombreField.setText(selectedServicio.getNombre());
            precioField.setText(selectedServicio.getPrecio());
        }
    }

    @FXML
    private void onEditarServicio() {
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