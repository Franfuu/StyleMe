package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.ConnectionMariaDB;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cita_ServicioDAO {
    private static final String INSERTNM = "INSERT INTO cita_servicio (Id_Cita, Id_Servicio) VALUES (?, ?)";
    private static final String DELETENM = "DELETE FROM cita_servicio WHERE Id_Cita = ? AND Id_Servicio = ?";

    public Cita_ServicioDAO() {
    }
    public static void insertServicioEnCita(int IdCita, int IdServicio) {
        // Use a try-with-resources to ensure that the PreparedStatement is closed automatically
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERTNM)) {
            // Set the parameters for the query
            pst.setInt(1, IdCita);
            pst.setInt(2, IdServicio);
            // Execute the insert query
            pst.executeUpdate();
        } catch (SQLException e) {
            // Specific exception handling for duplicate entries
            if (e.getSQLState().equals("23000") && e.getErrorCode() == 1062) {
                // If a duplicate entry is found, show an alert to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error: Duplicate entry found.");
                alert.show();
            } else {
                // For other errors, print the stack trace
                e.printStackTrace();
            }
        }
    }


    public static boolean deleteServicioEnCita(int IdCita, int IdServicio) throws SQLException {
        // Use a try-with-resources to ensure that the PreparedStatement is closed automatically
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETENM)) {
            // Set the parameters for the query
            pst.setInt(1, IdCita);
            pst.setInt(2, IdServicio);
            // Execute the delete query and get the number of affected rows
            int rowsAffected = pst.executeUpdate();
            // Return true if any rows were affected, indicating that the deletion was successful
            return rowsAffected > 0;
        }
    }
}
