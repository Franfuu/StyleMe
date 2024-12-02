package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.CitaDAO;
import com.github.Franfuu.model.entity.Cita;
import com.github.Franfuu.model.entity.ControlSesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ClienteEECitas extends Controller implements Initializable {

    @FXML
    private TableView<Cita> citasTable; // Tabla para mostrar las citas

    @FXML
    private TableColumn<Cita, String> fechaColumn; // Columna para la fecha de la cita

    @FXML
    private TableColumn<Cita, String> horaColumn; // Columna para la hora de la cita

    @FXML
    private TableColumn<Cita, String> peluqueroColumn; // Columna para el peluquero de la cita

    @FXML
    private TableColumn<Cita, Void> deleteColumn; // Columna para el botón de eliminar

    private ObservableList<Cita> citasList; // Lista observable de citas

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar las columnas de la tabla
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("hora"));
        peluqueroColumn.setCellValueFactory(new PropertyValueFactory<>("idPeluquero"));

        addButtonToTable(); // Añadir botón de eliminar a la tabla
        addDoubleClickToEdit(); // Añadir doble clic para editar

        loadCitas(); // Cargar las citas
    }

    private void loadCitas() {
        // Cargar las citas del cliente logueado
        int clienteId = ControlSesion.getInstance().getLoggedInClienteId();
        CitaDAO citaDAO = new CitaDAO();
        List<Cita> citas = citaDAO.findByClienteId(clienteId);
        citasList = FXCollections.observableArrayList(citas);
        citasTable.setItems(citasList);
    }

    private void addButtonToTable() {
        // Añadir botón de eliminar a cada fila de la tabla
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Eliminar");

            {
                deleteButton.setOnAction(event -> {
                    Cita cita = getTableView().getItems().get(getIndex());
                    onDeleteCita(cita);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void addDoubleClickToEdit() {
        // Añadir doble clic para editar la observación de la cita
        citasTable.setRowFactory(tv -> {
            TableRow<Cita> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Cita rowData = row.getItem();
                    onEditCita(rowData);
                }
            });
            return row;
        });
    }

    private void onEditCita(Cita cita) {
        // Editar la observación de la cita
        TextInputDialog dialog = new TextInputDialog(cita.getObservacion());
        dialog.setTitle("Editar Observación");
        dialog.setHeaderText("Editar Observación de la Cita");
        dialog.setContentText("Observación:");

        dialog.showAndWait().ifPresent(observacion -> {
            cita.setObservacion(observacion);
            CitaDAO citaDAO = new CitaDAO();
            citaDAO.update(cita);
            loadCitas();
        });
    }

    private void onDeleteCita(Cita cita) {
        // Eliminar la cita seleccionada
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

    @FXML
    private void onVolver() {
        // Volver a la escena anterior
        try {
            App.currentController.changeScene(Scenes.CLIENTELISTA, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(Object input) {
        // Método llamado al abrir la vista
    }

    @Override
    public void onClose(Object output) {
        // Método llamado al cerrar la vista
    }
}