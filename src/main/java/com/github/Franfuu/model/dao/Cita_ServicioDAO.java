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
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERTNM)) {
            pst.setInt(1, IdCita);
            pst.setInt(2, IdServicio);
            pst.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000") && e.getErrorCode() == 1062) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error: Duplicate entry found.");
                alert.show();
            } else {
                e.printStackTrace();
            }
        }
    }


    public static boolean deleteServicioEnCita(int IdCita, int IdServicio) throws SQLException {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETENM)) {
            pst.setInt(1, IdCita);
            pst.setInt(2, IdServicio);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
