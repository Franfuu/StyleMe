package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.ConnectionMariaDB;
import com.github.Franfuu.model.entity.Servicio;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cita_ServicioDAO {
    private static final String INSERTNM = "INSERT INTO cita_servicio (Id_Cita, Id_Servicio) VALUES (?, ?)";
    private static final String DELETENM = "DELETE FROM cita_servicio WHERE Id_Cita = ? AND Id_Servicio = ?";
    private static final String FIND_SERVICIOS_BY_CITA = "SELECT s.* FROM servicio s JOIN cita_servicio cs ON s.Id = cs.Id_Servicio WHERE cs.Id_Cita = ?";
    private static final String CHECK_SERVICIO_IN_CITA = "SELECT COUNT(*) FROM cita_servicio WHERE Id_Cita = ? AND Id_Servicio = ?";

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
                alert.setContentText("Error: El servicio ya está asignado a la cita.");
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

    public static boolean isServicioInCita(int IdCita, int IdServicio) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(CHECK_SERVICIO_IN_CITA)) {
            pst.setInt(1, IdCita);
            pst.setInt(2, IdServicio);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Servicio> findServiciosByCita(int idCita) {
        List<Servicio> servicios = new ArrayList<>();
        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SERVICIOS_BY_CITA)) {

            statement.setInt(1, idCita);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Servicio servicio = new Servicio();
                    servicio.setId(resultSet.getInt("Id"));
                    servicio.setNombre(resultSet.getString("Nombre"));
                    servicio.setPrecio(resultSet.getString("Precio"));
                    servicios.add(servicio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicios;
    }

}


