package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.Cita_ServicioDAO;
import com.github.Franfuu.model.dao.ServicioDAO;
import com.github.Franfuu.model.entity.Cita;
import com.github.Franfuu.model.entity.Servicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.Franfuu.model.dao.Cita_ServicioDAO.isServicioInCita;

public class CitaServicio extends Controller implements Initializable {

    @FXML
    private ComboBox<Servicio> servicioComboBox;

    private Cita selectedCita;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadServicios();
    }

    private void loadServicios() {
        ServicioDAO servicioDAO = new ServicioDAO();
        List<Servicio> servicios = servicioDAO.findAll();
        ObservableList<Servicio> servicioList = FXCollections.observableArrayList(servicios);
        servicioComboBox.setItems(servicioList);
    }

    @FXML
    private void onAddService() {
        Servicio selectedServicio = servicioComboBox.getSelectionModel().getSelectedItem();
        if (selectedServicio == null) {
            showAlert("Error", "Debe seleccionar un servicio.");
            return;
        }
       if (isServicioInCita(selectedCita.getId(), selectedServicio.getId())) {
            showAlert("Error", "El servicio ya está añadido a la cita.");
            return;
        }


        Cita_ServicioDAO.insertServicioEnCita(selectedCita.getId(), selectedServicio.getId());
        showAlert("Éxito", "Servicio añadido a la cita exitosamente.");
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
        if (input instanceof Cita) {
            selectedCita = (Cita) input;
        }
    }

    @Override
    public void onClose(Object output) {
    }
}