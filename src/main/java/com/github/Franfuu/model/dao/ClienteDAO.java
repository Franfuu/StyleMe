package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.ConnectionMariaDB;
import com.github.Franfuu.model.entity.Cliente;

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

public class ClienteDAO {
    // Consulta SQL para recuperar todos los clientes
    private static final String FINDALL = "SELECT * FROM cliente";
    // Consulta SQL para buscar un cliente por su ID
    private static final String FINDBYID = "SELECT * FROM cliente WHERE Id=?";
    // Consulta SQL para insertar un nuevo cliente
    private static final String INSERT = "INSERT INTO cliente (Nombre, Apellido, Telefono, Correo, Genero, Contraseña) VALUES (?,?,?,?,?,?)";
    // Consulta SQL para actualizar un cliente existente
    private static final String UPDATE = "UPDATE cliente SET Nombre=?, Apellido=?, Telefono=?, Correo=?, Genero=?, Contraseña=? WHERE Id=?";
    // Consulta SQL para eliminar un cliente por su ID
    private static final String DELETE = "DELETE FROM cliente WHERE Id=?";

    public ClienteDAO() {
    }

    // Guarda un nuevo cliente en la base de datos
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

    // Busca un cliente por su ID
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

    // Consulta SQL para buscar un cliente por su correo y contraseña
    private static final String FIND_BY_EMAIL_AND_PASSWORD = "SELECT * FROM cliente WHERE Correo = ? AND Contraseña = ?";

    // Busca un cliente por su correo y contraseña
    public Cliente findByEmailAndPassword(String email, String password) {
        Cliente cliente = null;
        try (Connection conn = ConnectionMariaDB.getConnection();
             PreparedStatement pst = conn.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD)) {
            pst.setString(1, email);
            pst.setString(2, hashPassword(password));
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("Id"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setApellido(rs.getString("Apellido"));
                    cliente.setTelefono(rs.getString("Telefono"));
                    cliente.setCorreo(rs.getString("Correo"));
                    cliente.setGenero(rs.getString("Genero"));
                    cliente.setContraseña(rs.getString("Contraseña"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
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

    // Actualiza un cliente existente en la base de datos
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

    // Elimina un cliente de la base de datos por su ID
    public boolean delete(int Id) throws SQLException {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setInt(1, Id);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Recupera todos los clientes de la base de datos
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

    // Crea una nueva instancia del DAO
    public static ClienteDAO build() {
        return new ClienteDAO();
    }

    // Verifica si un correo ya está registrado en la base de datos
    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM cliente WHERE Correo = ?";
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
        String query = "SELECT COUNT(*) FROM cliente WHERE Telefono = ?";
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