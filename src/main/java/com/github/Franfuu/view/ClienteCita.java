package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.CitaDAO;
import com.github.Franfuu.model.entity.Cita;
import com.github.Franfuu.model.entity.ControlSesion;
import com.github.Franfuu.model.entity.Peluquero;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.LocalTimeStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ClienteCita extends Controller implements Initializable {

    @FXML
    private DatePicker fechaDatePicker; // Selector de fecha
    @FXML
    private Spinner<LocalTime> horaSpinner; // Selector de hora
    @FXML
    private TextField observacionField; // Campo de texto para observaciones
    @FXML
    private Button createCitaButton; // Botón para crear cita
    @FXML
    private Button volverButton; // Botón para volver

    private Peluquero selectedPeluquero; // Peluquero seleccionado

    @Override
    public void onOpen(Object input) throws Exception {
        // Método llamado al abrir la vista
        if (input instanceof Peluquero) {
            this.selectedPeluquero = (Peluquero) input;
        }
    }

    @Override
    public void onClose(Object output) {
        // Método llamado al cerrar la vista
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Método llamado automáticamente después de cargar el archivo FXML
        createCitaButton.setOnAction(event -> createCita());

        // Configurar el DatePicker
        fechaDatePicker.setValue(LocalDate.now());

        // Configurar el Spinner para la selección de hora
        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:00"), null));
                setValue(LocalTime.now());
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusHours(steps));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusHours(steps));
            }
        };
        horaSpinner.setValueFactory(valueFactory);
    }

    private void createCita() {
        // Crear una nueva cita
        int clienteId = ControlSesion.getInstance().getLoggedInClienteId();
        if (clienteId <= 0) {
            showAlert("Error", "No hay cliente logueado o el ID del cliente es inválido");
            return;
        }

        LocalDate selectedDate = fechaDatePicker.getValue();
        LocalTime selectedTime = horaSpinner.getValue();
        if (selectedDate.equals(LocalDate.now()) && selectedTime.isBefore(LocalTime.now())) {
            showAlert("Error", "No se puede crear una cita para una hora pasada");
            return;
        }

        if (selectedDate.isBefore(LocalDate.now()) || (selectedDate.equals(LocalDate.now()) && selectedTime.isBefore(LocalTime.now()))) {
            showAlert("Error", "No se puede crear una cita para una fecha u hora pasada");
            return;
        }

        Cita cita = new Cita();
        cita.setFecha(selectedDate.toString());
        cita.setHora(selectedTime.getHour() + ":00");
        cita.setObservacion(observacionField.getText());
        cita.setIdCliente(clienteId);
        cita.setIdPeluquero(selectedPeluquero.getId());

        CitaDAO citaDAO = new CitaDAO();
        boolean success = citaDAO.createCita(cita);

        if (success) {
            showAlert("Éxito", "Cita creada exitosamente");
        } else {
            showAlert("Error", "Error al crear la cita");
        }
    }

    private void showAlert(String title, String message) {
        // Mostrar una alerta con el título y mensaje especificados
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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
}