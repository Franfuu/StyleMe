package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.ConnectionMariaDB;
import com.github.Franfuu.model.entity.Cita;
import com.github.Franfuu.model.entity.Servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {
    private static final String INSERT = "INSERT INTO servicio (Nombre, Precio) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE servicio SET Nombre = ?, Precio = ? WHERE Id = ?";
    private static final String FINDALL = "SELECT * FROM servicio";
    private static final String DELETE = "DELETE FROM servicio WHERE Id = ?";

    public void save(Servicio servicio) {
        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, servicio.getNombre());
            statement.setString(2, servicio.getPrecio());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    servicio.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Servicio servicio) {

        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            statement.setString(1, servicio.getNombre());
            statement.setString(2, servicio.getPrecio());
            statement.setInt(3, servicio.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Servicio> findAll() {
        List<Servicio> servicios = new ArrayList<>();
        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(FINDALL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Servicio servicio = new Servicio();
                servicio.setId(resultSet.getInt("Id"));
                servicio.setNombre(resultSet.getString("Nombre"));
                servicio.setPrecio(resultSet.getString("Precio"));
                servicios.add(servicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicios;
    }


}