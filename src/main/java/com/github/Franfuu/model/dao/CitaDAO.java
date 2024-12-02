package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.ConnectionMariaDB;
import com.github.Franfuu.model.entity.Cita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    // Consulta SQL para insertar una nueva cita en la base de datos
    private static final String INSERT = "INSERT INTO cita (Fecha, Hora, Observacion, Id_Cliente, Id_Peluquero) VALUES (?, ?, ?, ?, ?)";

    // Consulta SQL para actualizar una cita existente
    private static final String UPDATE = "UPDATE cita SET Fecha = ?, Hora = ?, Observacion = ?, Id_Cliente = ?, Id_Peluquero = ? WHERE Id = ?";

    // Consulta SQL para recuperar todos los IDs de las citas
    private static final String FINDALL = "SELECT Id FROM cita";

    // Consulta SQL para buscar una cita por su ID
    private static final String FINDBYID = "SELECT Id FROM cita WHERE Id = ?";

    // Consulta SQL para eliminar una cita por su ID
    private static final String DELETE = "DELETE FROM cita WHERE Id = ?";

    // Consulta SQL para buscar citas asociadas a un peluquero específico
    private static final String FINDPELUQUEROID = "SELECT Id, Fecha, Hora, Observacion FROM cita WHERE Id_Peluquero = ?";

    // Consulta SQL para buscar citas asociadas a un cliente específico
    private static final String FINDCLIENTEID = "SELECT * FROM cita WHERE Id_Cliente = ?";

    // Constructor vacío para la clase DAO
    public CitaDAO() {
    }

    // Recupera una lista de citas asociadas a un peluquero específico
    public List<Cita> findByPeluqueroId(int peluqueroId) {
        List<Cita> citas = new ArrayList<>();
        try (Connection conn = ConnectionMariaDB.getConnection();
             PreparedStatement pst = conn.prepareStatement(FINDPELUQUEROID)) {
            pst.setInt(1, peluqueroId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();
                    cita.setId(rs.getInt("Id"));
                    cita.setFecha(rs.getString("Fecha"));
                    cita.setHora(rs.getString("Hora"));
                    cita.setObservacion(rs.getString("Observacion"));
                    citas.add(cita);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    // Recupera una lista de citas asociadas a un cliente específico
    public List<Cita> findByClienteId(int clienteId) {
        List<Cita> citas = new ArrayList<>();
        try (Connection conn = ConnectionMariaDB.getConnection();
             PreparedStatement pst = conn.prepareStatement(FINDCLIENTEID)) {
            pst.setInt(1, clienteId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();
                    cita.setId(rs.getInt("Id"));
                    cita.setFecha(rs.getString("Fecha"));
                    cita.setHora(rs.getString("Hora"));
                    cita.setObservacion(rs.getString("Observacion"));
                    cita.setIdCliente(rs.getInt("Id_Cliente"));
                    cita.setIdPeluquero(rs.getInt("Id_Peluquero"));
                    citas.add(cita);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    // Inserta una nueva cita en la base de datos, asegurándose de que no haya duplicados
    public boolean createCita(Cita cita) {
        if (citaExists(cita.getFecha(), cita.getHora())) {
            System.out.println("No puedes crear la cita porque ya existe una cita en esa fecha y hora");
            return false;
        }

        try (Connection conn = ConnectionMariaDB.getConnection()) {
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Connection is closed or null");
            }

            conn.setAutoCommit(false); // Desactiva el auto-commit para manejo manual de transacciones

            try (PreparedStatement pstCita = conn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstCita.setString(1, cita.getFecha());
                pstCita.setString(2, cita.getHora());
                pstCita.setString(3, cita.getObservacion());
                pstCita.setInt(4, cita.getIdCliente());
                pstCita.setInt(5, cita.getIdPeluquero());
                pstCita.executeUpdate();

                try (ResultSet generatedKeys = pstCita.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int citaId = generatedKeys.getInt(1); // Recupera el ID generado automáticamente
                    } else {
                        conn.rollback(); // Revierte la transacción si no se genera un ID
                        return false;
                    }
                }

                conn.commit(); // Confirma la transacción
                return true;
            } catch (SQLException e) {
                conn.rollback(); // Revierte la transacción en caso de error
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verifica si ya existe una cita en una fecha y hora específicas
    private boolean citaExists(String fecha, String hora) {
        try (Connection conn = ConnectionMariaDB.getConnection()) {
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Connection is closed or null");
            }
            try (PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) FROM cita WHERE Fecha = ? AND Hora = ?")) {
                pst.setString(1, fecha);
                pst.setString(2, hora);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0; // Retorna true si hay al menos una cita
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Busca una cita por su ID
    public Cita findById(int Id) {
        Cita result = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            pst.setInt(1, Id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Cita c = new Cita();
                    c.setId(rs.getInt("Id"));
                    c.setFecha(rs.getString("Fecha"));
                    c.setHora(rs.getString("Hora"));
                    c.setObservacion(rs.getString("Observacion"));
                    c.setIdCliente(rs.getInt("Id_Cliente"));
                    c.setIdPeluquero(rs.getInt("Id_Peluquero"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Actualiza una cita existente en la base de datos
    public Cita update(Cita entity) {
        Cita result = new Cita();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            pst.setString(1, entity.getFecha());
            pst.setString(2, entity.getHora());
            pst.setString(3, entity.getObservacion());
            pst.setInt(4, entity.getIdCliente());
            pst.setInt(5, entity.getIdPeluquero());
            pst.setInt(6, entity.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Elimina una cita de la base de datos por su ID
    public boolean delete(int Id) throws SQLException {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setInt(1, Id);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Recupera todas las citas de la tabla
    public static List<Cita> findAll() {
        List<Cita> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Cita c = new Cita();
                    c.setId(rs.getInt("Id"));
                    c.setFecha(rs.getString("Fecha"));
                    c.setHora(rs.getString("Hora"));
                    c.setObservacion(rs.getString("Observacion"));
                    c.setIdCliente(rs.getInt("Id_Cliente"));
                    c.setIdPeluquero(rs.getInt("Id_Peluquero"));
                    result.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Crea una nueva instancia del DAO
    public static CitaDAO build() {
        return new CitaDAO();
    }
}
