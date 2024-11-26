package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.ConnectionMariaDB;
import com.github.Franfuu.model.entity.Servicio;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {
    private static final String INSERT = "INSERT INTO servicio (Id, Nombre, Precio) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE servicio SET Nombre = ?, Precio = ? WHERE Id = ?";
    private static final String FINDALL = "SELECT Id FROM servicio";
    private static final String FINDBYID = "SELECT Id FROM servicio WHERE Id = ?";
    private static final String DELETE = "DELETE FROM servicio WHERE Id = ?";
    //private static final String FINDSERVICIOSBYCITA = "SELECT * FROM servicio WHERE Id IN (SELECT IdServicio FROM cita WHERE Id = ?)";

    public ServicioDAO() {
    }
    public Servicio save(Servicio entity) {
        Servicio result = new Servicio();
        if (entity == null || entity.getId() != 0) return result;
        try {
            if (findById(entity.getId()) == null) {
                try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                    pst.setString(1, entity.getNombre());
                    pst.setString(2, entity.getPrecio());
                    pst.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public Servicio findById(int Id) {
        Servicio result = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            pst.setInt(1, Id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Servicio s = new Servicio();
                    s.setId(rs.getInt("Id"));
                    s.setNombre(rs.getString("Nombre"));
                    s.setPrecio(rs.getString("Precio"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Servicio update(Servicio entity) {
        Servicio result = new Servicio();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            pst.setString(1, entity.getNombre());
            pst.setString(2, entity.getPrecio());
            pst.setInt(3, entity.getId());
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

    public static List<Servicio> findAll() {
        List<Servicio> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Servicio s = new Servicio();
                    s.setId(rs.getInt("Id"));
                    s.setNombre(rs.getString("Nombre"));
                    s.setPrecio(rs.getString("Apellido"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }



    public static ServicioDAO build() {
        return new ServicioDAO();
    }
}
