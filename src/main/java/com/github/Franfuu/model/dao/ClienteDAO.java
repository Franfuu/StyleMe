package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.ConnectionMariaDB;
import com.github.Franfuu.model.entity.Cliente;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private static final String FINDALL = "SELECT * FROM cliente";
    private static final String FINDBYID = "SELECT * FROM cliente WHERE Id=?";
    private static final String INSERT = "INSERT INTO cliente (Nombre, Apellido, Telefono, Correo, Genero, Contraseña) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE cliente SET Nombre=?, Apellido=?, Telefono=?, Correo=?, Genero=?, Contraseña=? WHERE Id=?";
    private static final String DELETE = "DELETE FROM cliente WHERE Id=?";

    public ClienteDAO() {
    }

    public Cliente save(Cliente entity) {
        Cliente result = new Cliente();
        if (entity == null || entity.getId() != 0) return result;
        try {
            if (findById(entity.getId()) == null) {
                try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                    pst.setString(1, entity.getNombre());
                    pst.setString(2, entity.getApellido());
                    pst.setString(3, entity.getTelefono());
                    pst.setString(4, entity.getCorreo());
                    pst.setString(5, entity.getGenero());
                    pst.setString(6, hashPassword(entity.getContraseña()));
                    pst.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public Cliente findById(int Id) {
        Cliente result = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            pst.setInt(1, Id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("Id"));
                    c.setNombre(rs.getString("Nombre"));
                    c.setApellido(rs.getString("Apellido"));
                    c.setTelefono(rs.getString("Telefono"));
                    c.setCorreo(rs.getString("Correo"));
                    c.setGenero(rs.getString("Genero"));
                    c.setContraseña(rs.getString("Contraseña"));
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

    public Cliente update(Cliente entity) {
        Cliente result = new Cliente();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            pst.setString(1, entity.getNombre());
            pst.setString(2, entity.getApellido());
            pst.setString(3, entity.getTelefono());
            pst.setString(4, entity.getCorreo());
            pst.setString(5, entity.getGenero());
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

    public static List<Cliente> findAll() {
        List<Cliente> result = new ArrayList<> ();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("Id"));
                    c.setNombre(rs.getString("Nombre"));
                    c.setApellido(rs.getString("Apellido"));
                    c.setTelefono(rs.getString("Telefono"));
                    c.setCorreo(rs.getString("Correo"));
                    c.setGenero(rs.getString("Genero"));
                    c.setContraseña(rs.getString("Contraseña"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ClienteDAO build() {
        return new ClienteDAO();
    }
}
