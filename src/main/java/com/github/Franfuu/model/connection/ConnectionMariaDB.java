package com.github.Franfuu.model.connection;

import com.github.Franfuu.utils.XMLManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMariaDB {
    private final static String FILE = "connection.xml";
    private static ConnectionMariaDB _instance;
    private ConnectionProperties properties;

    private ConnectionMariaDB() {
        properties = (ConnectionProperties) XMLManager.readXML(new ConnectionProperties(), FILE);
    }

    public static Connection getConnection() {
        if (_instance == null) {
            _instance = new ConnectionMariaDB();
        }
        try {
            return DriverManager.getConnection(_instance.properties.getURL(), _instance.properties.getUser(), _instance.properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}