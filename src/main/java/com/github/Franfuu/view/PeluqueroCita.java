package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.CitaDAO;
import com.github.Franfuu.model.dao.Cita_ServicioDAO;
import com.github.Franfuu.model.entity.Cita;
import com.github.Franfuu.model.entity.ControlSesion;
import com.github.Franfuu.model.entity.Servicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PeluqueroCita extends Controller implements Initializable {

    @FXML
    private TableView<Cita> citasTable;
    @FXML
    private TableColumn<Cita, String> fechaColumn;
    @FXML
    private TableColumn<Cita, String> horaColumn;
    @FXML
    private TableColumn<Cita, String> observacionColumn;
    @FXML
    private TableColumn<Cita, Button> accionesColumn;
    @FXML
    private TableColumn<Cita, Button> serviciosColumn;
    @FXML
    private TableColumn<Cita, Button> eliminarServicioColumn;

    private ObservableList<Cita> citasList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("hora"));
        observacionColumn.setCellValueFactory(new PropertyValueFactory<>("observacion"));
        accionesColumn.setCellValueFactory(new PropertyValueFactory<>("acciones"));
        serviciosColumn.setCellValueFactory(new PropertyValueFactory<>("servicios"));
        eliminarServicioColumn.setCellValueFactory(new PropertyValueFactory<>("eliminarServicio"));

        loadCitas();
    }

    private void loadCitas() {
        int peluqueroId = ControlSesion.getInstance().getLoggedInClienteId();
        CitaDAO citaDAO = new CitaDAO();
        citasList = FXCollections.observableArrayList(citaDAO.findByPeluqueroId(peluqueroId));

        for (Cita cita : citasList) {
            Button deleteButton = new Button("Eliminar");
            deleteButton.setOnAction(event -> {
                try {
                    deleteCita(cita);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            cita.setAcciones(deleteButton);

            Button addServiceButton = new Button("Añadir Servicio");
            addServiceButton.setOnAction(event -> onAddService(cita));
            cita.setServicios(addServiceButton);

            Button removeServiceButton = new Button("Eliminar Servicio");
            removeServiceButton.setOnAction(event -> onRemoveService(cita));
            cita.setEliminarServicio(removeServiceButton);
        }

        citasTable.setItems(citasList);
    }

    private void onRemoveService(Cita cita) {
        try {
            App.currentController.changeScene(Scenes.ELIMINARSERVICIO, cita);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCita(Cita cita) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Cita");
        alert.setHeaderText("Estas seguro que deseas eliminar esta cita?");
        alert.setContentText("Esta acción no se puede deshacer.");

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                CitaDAO citaDAO = new CitaDAO();
                try {
                    citaDAO.delete(cita.getId());
                    loadCitas();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void onAddService(Cita cita) {
        try {
            App.currentController.changeScene(Scenes.CITASERVICIO, cita);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void onCrearServicio() {
        try {
            App.currentController.changeScene(Scenes.PELUSERVICIO, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onEditarEliminarServicio() {
        try {
            App.currentController.changeScene(Scenes.EESERVICIO, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(Object input) throws Exception {
    }

    @Override
    public void onClose(Object output) {
    }
}