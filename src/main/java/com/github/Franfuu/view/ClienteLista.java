package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.CitaDAO;
import com.github.Franfuu.model.dao.PeluqueroDAO;
import com.github.Franfuu.model.entity.Cita;
import com.github.Franfuu.model.entity.ControlSesion;
import com.github.Franfuu.model.entity.Peluquero;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClienteLista extends Controller implements Initializable {

    @FXML
    private Label clientNameLabel; // Etiqueta para mostrar el nombre del cliente

    @FXML
    private TableView<Peluquero> peluqueroTable; // Tabla para mostrar los peluqueros

    @FXML
    private TableColumn<Peluquero, String> nameColumn; // Columna para el nombre del peluquero

    @FXML
    private TableColumn<Peluquero, String> specialtyColumn; // Columna para la especialidad del peluquero

    @FXML
    private TableColumn<Peluquero, String> phoneColumn; // Columna para el teléfono del peluquero

    private ObservableList<Peluquero> peluqueroList; // Lista observable de peluqueros

    @Override
    public void onOpen(Object input) throws Exception {
        // Método llamado al abrir la vista
        loadPeluqueroData();
    }

    @Override
    public void onClose(Object output) {
        // Método llamado al cerrar la vista
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Método llamado automáticamente después de cargar el archivo FXML
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("Especialidad"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Telefono"));

        peluqueroTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && peluqueroTable.getSelectionModel().getSelectedItem() != null) {
                Peluquero selectedPeluquero = peluqueroTable.getSelectionModel().getSelectedItem();
                try {
                    App.currentController.changeScene(Scenes.CLIENTECITA, selectedPeluquero);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void onEECitas() {
        // Método llamado al hacer clic en el botón para ver y editar citas
        try {
            App.currentController.changeScene(Scenes.CLIENTEEECITA, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPeluqueroData() {
        // Cargar los datos de los peluqueros desde el DAO
        PeluqueroDAO peluqueroDAO = new PeluqueroDAO();
        List<Peluquero> peluqueros = peluqueroDAO.findAll();
        peluqueroList = FXCollections.observableArrayList(peluqueros);
        peluqueroTable.setItems(peluqueroList);
    }

    private void showAlert(String title, String message) {
        // Mostrar una alerta con el título y mensaje especificados
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}