package com.github.Franfuu.view;

import com.github.Franfuu.model.dao.CitaDAO;
import com.github.Franfuu.model.entity.Cita;
import com.github.Franfuu.model.entity.ControlSesion;
import com.github.Franfuu.model.entity.Peluquero;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
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

    private Peluquero selectedPeluquero;

    @Override
    public void onOpen(Object input) throws Exception {
        if (input instanceof Peluquero) {
            this.selectedPeluquero = (Peluquero) input;
        }
    }

    @Override
    public void onClose(Object output) {
        // Cleanup if needed
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createCitaButton.setOnAction(event -> createCita());

        // Configure the DatePicker
        fechaDatePicker.setValue(LocalDate.now());

        // Configure the Spinner for time selection
        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"), null));
                setValue(LocalTime.now());
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps));
            }
        };
        horaSpinner.setValueFactory(valueFactory);
    }

    private void createCita() {
        int clienteId = ControlSesion.getInstance().getLoggedInClienteId();
        if (clienteId <= 0) {
            System.out.println("No cliente is logged in or invalid cliente ID");
            return;
        }

        Cita cita = new Cita();
        cita.setFecha(fechaDatePicker.getValue().toString());
        cita.setHora(horaSpinner.getValue().toString());
        cita.setObservacion(observacionField.getText());
        cita.setIdCliente(clienteId);
        cita.setIdPeluquero(selectedPeluquero.getId());

        CitaDAO citaDAO = new CitaDAO();
        boolean success = citaDAO.createCita(cita);

        if (success) {
            System.out.println("Cita created successfully");
        } else {
            System.out.println("Failed to create Cita");
        }
    }
}