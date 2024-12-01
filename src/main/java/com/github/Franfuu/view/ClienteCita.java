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
    private DatePicker fechaDatePicker;
    @FXML
    private Spinner<LocalTime> horaSpinner;
    @FXML
    private TextField observacionField;
    @FXML
    private Button createCitaButton;
    @FXML
    private Button volverButton;

    private Peluquero selectedPeluquero;

    @Override
    public void onOpen(Object input) throws Exception {
        if (input instanceof Peluquero) {
            this.selectedPeluquero = (Peluquero) input;
        }
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createCitaButton.setOnAction(event -> createCita());

        // Configure the DatePicker
        fechaDatePicker.setValue(LocalDate.now());

        // Configure the Spinner for time selection
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void onVolver() {
        try {
            App.currentController.changeScene(Scenes.CLIENTELISTA, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}