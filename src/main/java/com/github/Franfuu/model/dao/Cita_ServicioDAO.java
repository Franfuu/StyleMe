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

    // Consulta SQL para insertar un nuevo registro en la tabla cita_servicio, asociando un servicio con una cita.
    private static final String INSERTNM = "INSERT INTO cita_servicio (Id_Cita, Id_Servicio) VALUES (?, ?)";

    // Consulta SQL para eliminar un registro de la tabla cita_servicio, disociando un servicio de una cita.
    private static final String DELETENM = "DELETE FROM cita_servicio WHERE Id_Cita = ? AND Id_Servicio = ?";

    // Consulta SQL para encontrar todos los servicios asociados a una cita específica.
    private static final String FIND_SERVICIOS_BY_CITA = "SELECT s.* FROM servicio s JOIN cita_servicio cs ON s.Id = cs.Id_Servicio WHERE cs.Id_Cita = ?";

    // Consulta SQL para verificar si un servicio ya está asociado a una cita específica.
    private static final String CHECK_SERVICIO_IN_CITA = "SELECT COUNT(*) FROM cita_servicio WHERE Id_Cita = ? AND Id_Servicio = ?";


    // Constructor vacío para permitir la creación de instancias.
    public Cita_ServicioDAO() {
    }

    // Inserta un registro en la tabla cita_servicio asociando un servicio a una cita.
    public static void insertServicioEnCita(int IdCita, int IdServicio) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERTNM)) {
            pst.setInt(1, IdCita); // Establece el ID de la cita.
            pst.setInt(2, IdServicio); // Establece el ID del servicio.
            pst.executeUpdate(); // Ejecuta la inserción.
        } catch (SQLException e) {
            // Manejo de excepciones para evitar duplicados en la relación cita-servicio.
            if (e.getSQLState().equals("23000") && e.getErrorCode() == 1062) {
                Alert alert = new Alert(Alert.AlertType.ERROR); // Muestra un mensaje de error al usuario.
                alert.setContentText("Error: El servicio ya está asignado a la cita.");
                alert.show();
            } else {
                e.printStackTrace(); // Imprime cualquier otro error en la consola.
            }
        }
    }

    // Elimina un registro en la tabla cita_servicio, disociando un servicio de una cita.
    public static boolean deleteServicioEnCita(int IdCita, int IdServicio) throws SQLException {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETENM)) {
            pst.setInt(1, IdCita); // Establece el ID de la cita.
            pst.setInt(2, IdServicio); // Establece el ID del servicio.
            int rowsAffected = pst.executeUpdate(); // Ejecuta la eliminación y obtiene el número de filas afectadas.
            return rowsAffected > 0; // Retorna true si al menos una fila fue eliminada.
        }
    }

    // Verifica si un servicio ya está asociado a una cita en la base de datos.
    public static boolean isServicioInCita(int IdCita, int IdServicio) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(CHECK_SERVICIO_IN_CITA)) {
            pst.setInt(1, IdCita); // Establece el ID de la cita.
            pst.setInt(2, IdServicio); // Establece el ID del servicio.
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) { // Verifica si hay resultados.
                    return rs.getInt(1) > 0; // Retorna true si el conteo es mayor que 0.
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el error en la consola.
        }
        return false; // Retorna false si ocurre un error o no hay resultados.
    }

    // Busca todos los servicios asociados a una cita específica.
    public List<Servicio> findServiciosByCita(int idCita) {
        List<Servicio> servicios = new ArrayList<>(); // Lista para almacenar los servicios encontrados.
        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SERVICIOS_BY_CITA)) {

            statement.setInt(1, idCita); // Establece el ID de la cita.
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) { // Recorre los resultados obtenidos.
                    Servicio servicio = new Servicio(); // Crea una nueva instancia de Servicio.
                    servicio.setId(resultSet.getInt("Id")); // Asigna el ID del servicio.
                    servicio.setNombre(resultSet.getString("Nombre")); // Asigna el nombre del servicio.
                    servicio.setPrecio(resultSet.getString("Precio")); // Asigna el precio del servicio.
                    servicios.add(servicio); // Añade el servicio a la lista.
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el error en la consola.
        }
        return servicios; // Retorna la lista de servicios asociados a la cita.
    }
}
