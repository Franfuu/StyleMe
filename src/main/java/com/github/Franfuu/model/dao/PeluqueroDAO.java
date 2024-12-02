package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.ConnectionMariaDB;
import com.github.Franfuu.model.entity.Peluquero;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeluqueroDAO {
    // Consulta SQL para recuperar todos los peluqueros
    private static final String FINDALL = "SELECT * FROM peluquero";
    // Consulta SQL para buscar un peluquero por su ID
    private static final String FINDBYID = "SELECT * FROM peluquero WHERE Id = ?";
    // Consulta SQL para insertar un nuevo peluquero
    private static final String INSERT = "INSERT INTO peluquero (Nombre, Apellido, Telefono, Correo, Especialidad, Contraseña) VALUES (?,?,?,?,?,?)";
    // Consulta SQL para actualizar un peluquero existente
    private static final String UPDATE = "UPDATE peluquero SET Nombre=?, Apellido=?, Telefono=?, Correo=?, Direccion=?, Especialidad = ?, Contraseña=? WHERE Id=?";
    // Consulta SQL para eliminar un peluquero por su ID
    private static final String DELETE = "DELETE FROM peluquero WHERE Id=?";
    // Consulta SQL para buscar un peluquero por su correo y contraseña
    private static final String FINDBYEMAILANDPASSWORD = "SELECT * FROM peluquero WHERE Correo = ? AND Contraseña = ?";

    public PeluqueroDAO() {
    }

    // Guarda un nuevo peluquero en la base de datos
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

    // Busca un peluquero por su ID
    public Peluquero findById(Integer Id) {
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

    // Busca un peluquero por su correo y contraseña
    public Peluquero findByEmailAndPassword(String email, String password) {
        Peluquero peluquero = null;
        try (Connection conn = ConnectionMariaDB.getConnection();
             PreparedStatement pst = conn.prepareStatement(FINDBYEMAILANDPASSWORD)) {
            pst.setString(1, email);
            pst.setString(2, hashPassword(password));
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    peluquero = new Peluquero();
                    peluquero.setId(rs.getInt("Id"));
                    peluquero.setNombre(rs.getString("Nombre"));
                    peluquero.setApellido(rs.getString("Apellido"));
                    peluquero.setTelefono(rs.getString("Telefono"));
                    peluquero.setCorreo(rs.getString("Correo"));
                    peluquero.setEspecialidad(rs.getString("Especialidad"));
                    peluquero.setContraseña(rs.getString("Contraseña"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peluquero;
    }

    // Hashea la contraseña usando SHA-256
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

    // Actualiza un peluquero existente en la base de datos
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

    // Elimina un peluquero de la base de datos por su ID
    public boolean delete(int Id) throws SQLException {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setInt(1, Id);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Recupera todos los peluqueros de la base de datos
    public List<Peluquero> findAll() {
        List<Peluquero> peluqueros = new ArrayList<>();
        try (Connection conn = ConnectionMariaDB.getConnection()) {
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Connection is closed or null");
            }
            try (PreparedStatement pst = conn.prepareStatement(FINDALL);
                 ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Peluquero peluquero = new Peluquero();
                    peluquero.setId(rs.getInt("Id"));
                    peluquero.setNombre(rs.getString("Nombre"));
                    peluquero.setEspecialidad(rs.getString("Especialidad"));
                    peluquero.setTelefono(rs.getString("Telefono"));
                    peluqueros.add(peluquero);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peluqueros;
    }

    // Crea una nueva instancia del DAO
    public static PeluqueroDAO build() {
        return new PeluqueroDAO();
    }

    // Verifica si un correo ya está registrado en la base de datos
    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM peluquero WHERE Correo = ?";
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(query)) {
            pst.setString(1, email);
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

    // Verifica si un número de teléfono ya está registrado en la base de datos
    public boolean phoneExists(String phone) {
        String query = "SELECT COUNT(*) FROM peluquero WHERE Telefono = ?";
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(query)) {
            pst.setString(1, phone);
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
}