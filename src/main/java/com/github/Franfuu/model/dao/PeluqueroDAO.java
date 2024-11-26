package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.ConnectionMariaDB;
import com.github.Franfuu.model.entity.Peluquero;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeluqueroDAO {
private static final String FINDALL = "SELECT * FROM peluquero";
private static final String FINDBYID = "SELECT * FROM peluquero WHERE Id = ?";
private static final String INSERT = "INSERT INTO peluquero (Nombre, Apellido, Email, Telefono, Especialidad, Password) VALUES (?,?,?,?,?,?,?,?)";
private static final String UPDATE = "UPDATE peluquero SET Nombre=?, Apellido=?, Email=?, Telefono=?, Direccion=?, Especialidad = ?, Password=? WHERE Id=?";
private static final String DELETE = "DELETE FROM peluquero WHERE Id=?";

public PeluqueroDAO() {
}

    public Peluquero save(Peluquero entity) {
        Peluquero result = new Peluquero();
        if (entity == null || entity.getId() != 0) return result;
        try {
            if (findById(entity.getId()) == null) {
                try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                    pst.setString(1, entity.getNombre());
                    pst.setString(2, entity.getApellido());
                    pst.setString(3, entity.getTelefono());
                    pst.setString(4, entity.getCorreo());
                    pst.setString(5, entity.getEspecialidad());
                    pst.setString(6, hashPassword(entity.getContraseña()));
                    pst.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public Peluquero findById(int Id) {
        Peluquero result = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            pst.setInt(1, Id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Peluquero p = new Peluquero();
                    p.setId(rs.getInt("Id"));
                    p.setNombre(rs.getString("Nombre"));
                    p.setApellido(rs.getString("Apellido"));
                    p.setTelefono(rs.getString("Telefono"));
                    p.setCorreo(rs.getString("Correo"));
                    p.setEspecialidad(rs.getString("Especialidad"));
                    p.setContraseña(rs.getString("Contraseña"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Peluquero update(Peluquero entity) {
        Peluquero result = new Peluquero();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            pst.setString(1, entity.getNombre());
            pst.setString(2, entity.getApellido());
            pst.setString(3, entity.getTelefono());
            pst.setString(4, entity.getCorreo());
            pst.setString(5, entity.getEspecialidad());
            pst.setString(6, entity.getContraseña());
            pst.setInt(7, entity.getId());
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

    public static List<Peluquero> findAll() {
        List<Peluquero> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Peluquero p = new Peluquero();
                    p.setId(rs.getInt("Id"));
                    p.setNombre(rs.getString("Nombre"));
                    p.setApellido(rs.getString("Apellido"));
                    p.setTelefono(rs.getString("Telefono"));
                    p.setCorreo(rs.getString("Correo"));
                    p.setEspecialidad(rs.getString("Especialidad"));
                    p.setContraseña(rs.getString("Contraseña"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static PeluqueroDAO build() {
        return new PeluqueroDAO();
    }
}
