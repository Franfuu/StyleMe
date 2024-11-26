package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.ConnectionMariaDB;
import com.github.Franfuu.model.entity.Cita;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CitaDAO {
    private static final String INSERT = "INSERT INTO cita (Fecha, Hora, Observacion, Id_cliente, Id_Peluquero) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cita SET Fecha = ?, Hora = ?, Observacion = ?, Id_Cliente = ?, Id_Peluquero = ? WHERE Id = ?";
    private static final String FINDALL = "SELECT Id FROM cita";
    private static final String FINDBYID = "SELECT Id FROM cita WHERE Id = ?";
    private static final String DELETE = "DELETE FROM cita WHERE Id = ?";
    //private static final String FINDCITASBYCLIENTE = "SELECT * FROM cita WHERE Id_Cliente = ?";
    //private static final String FINDCITASBYPELUQUERO = "SELECT * FROM cita WHERE Id_Peluquero = ?";
    //private static final String FINDCITASBYFECHA = "SELECT * FROM cita WHERE Fecha = ?";
    //private static final String FINDCITASBYHORA = "SELECT * FROM cita WHERE Hora = ?";

    public CitaDAO() {
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
        List<Cita> result = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
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

    public static CitaDAO build() {
        return new CitaDAO();
    }
}
