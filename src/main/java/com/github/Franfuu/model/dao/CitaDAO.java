package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.ConnectionMariaDB;
import com.github.Franfuu.model.entity.Cita;
import com.github.Franfuu.model.entity.Servicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    private static final String INSERT = "INSERT INTO cita (Fecha, Hora, Observacion, Id_Cliente, Id_Peluquero) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cita SET Fecha = ?, Hora = ?, Observacion = ?, Id_Cliente = ?, Id_Peluquero = ? WHERE Id = ?";
    private static final String FINDALL = "SELECT Id FROM cita";
    private static final String FINDBYID = "SELECT Id FROM cita WHERE Id = ?";
    private static final String DELETE = "DELETE FROM cita WHERE Id = ?";
    private static final String FINDPELUQUEROID = "SELECT Id, Fecha, Hora, Observacion FROM cita WHERE Id_Peluquero = ?";
    private static final String FINDCLIENTEID = "SELECT * FROM cita WHERE Id_Cliente = ?";
    public CitaDAO() {
    }


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

    public boolean createCita(Cita cita) {
        if (citaExists(cita.getFecha(), cita.getHora())) {
            System.out.println("No puedes crear la cita porque ya existe una cita en esa fecha y hora");
            return false;
        }

        try (Connection conn = ConnectionMariaDB.getConnection()) {
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Connection is closed or null");
            }

            conn.setAutoCommit(false);

            try (PreparedStatement pstCita = conn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstCita.setString(1, cita.getFecha());
                pstCita.setString(2, cita.getHora());
                pstCita.setString(3, cita.getObservacion());
                pstCita.setInt(4, cita.getIdCliente());
                pstCita.setInt(5, cita.getIdPeluquero());
                pstCita.executeUpdate();

                try (ResultSet generatedKeys = pstCita.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int citaId = generatedKeys.getInt(1);
                    } else {
                        conn.rollback();
                        return false;
                    }
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



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
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public Cita save(Cita entity) {
        Cita result = new Cita();
        if (entity == null || entity.getId() != 0) return result;
        try {
            if (findById(entity.getId()) == null) {
                try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                    pst.setString(1, entity.getFecha());
                    pst.setString(2, entity.getHora());
                    pst.setString(3, entity.getObservacion());
                    pst.setInt(4, entity.getIdCliente());
                    pst.setInt(5, entity.getIdPeluquero());
                    pst.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Cita> findPendingCitas() {
        List<Cita> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement("SELECT * FROM cita")) {
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

    public boolean delete(int Id) throws SQLException {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setInt(1, Id);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        }
    }

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

    public static CitaDAO build() {
        return new CitaDAO();
    }



}