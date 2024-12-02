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
    private TableView<Cita> citasTable; // Tabla para mostrar las citas
    @FXML
    private TableColumn<Cita, String> fechaColumn; // Columna para la fecha de la cita
    @FXML
    private TableColumn<Cita, String> horaColumn; // Columna para la hora de la cita
    @FXML
    private TableColumn<Cita, String> observacionColumn; // Columna para las observaciones de la cita
    @FXML
    private TableColumn<Cita, Button> accionesColumn; // Columna para los botones de acciones
    @FXML
    private TableColumn<Cita, Button> serviciosColumn; // Columna para los botones de servicios
    @FXML
    private TableColumn<Cita, Button> eliminarServicioColumn; // Columna para los botones de eliminar servicio

    private ObservableList<Cita> citasList; // Lista observable de citas

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializar las columnas de la tabla con las propiedades correspondientes
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("hora"));
        observacionColumn.setCellValueFactory(new PropertyValueFactory<>("observacion"));
        accionesColumn.setCellValueFactory(new PropertyValueFactory<>("acciones"));
        serviciosColumn.setCellValueFactory(new PropertyValueFactory<>("servicios"));
        eliminarServicioColumn.setCellValueFactory(new PropertyValueFactory<>("eliminarServicio"));

        // Cargar las citas en la tabla
        loadCitas();
    }

    private void loadCitas() {
        // Obtener el ID del peluquero logueado
        int peluqueroId = ControlSesion.getInstance().getLoggedInClienteId();
        CitaDAO citaDAO = new CitaDAO();
        // Obtener las citas del peluquero y cargarlas en la lista observable
        citasList = FXCollections.observableArrayList(citaDAO.findByPeluqueroId(peluqueroId));

        // Configurar los botones de acciones para cada cita
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

            Button addServiceButton = new Button("Añadir");
            addServiceButton.setOnAction(event -> onAddService(cita));
            cita.setServicios(addServiceButton);

            Button removeServiceButton = new Button("Eliminar Servicio");
            removeServiceButton.setOnAction(event -> onRemoveService(cita));
            cita.setEliminarServicio(removeServiceButton);
        }

        // Establecer la lista de citas en la tabla
        citasTable.setItems(citasList);
    }

    private void onRemoveService(Cita cita) {
        // Cambiar a la escena de eliminar servicio
        try {
            App.currentController.changeScene(Scenes.ELIMINARSERVICIO, cita);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCita(Cita cita) throws SQLException {
        // Mostrar una alerta de confirmación para eliminar la cita
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Cita");
        alert.setHeaderText("Estas seguro que deseas eliminar esta cita?");
        alert.setContentText("Esta acción no se puede deshacer.");

        // Si el usuario confirma, eliminar la cita
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
        // Cambiar a la escena de añadir servicio
        try {
            App.currentController.changeScene(Scenes.CITASERVICIO, cita);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCrearServicio() {
        // Cambiar a la escena de crear servicio
        try {
            App.currentController.changeScene(Scenes.PELUSERVICIO, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onEditarEliminarServicio() {
        // Cambiar a la escena de editar/eliminar servicio
        try {
            App.currentController.changeScene(Scenes.EESERVICIO, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
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