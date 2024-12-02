package com.github.Franfuu.model.connection;

import com.github.Franfuu.utils.XMLManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMariaDB {
    private final static String FILE = "connection.xml"; // Archivo XML donde se almacenan las propiedades de conexión.
    private static ConnectionMariaDB _instance; // Instancia única de la clase para implementar el patrón Singleton.
    private ConnectionProperties properties; // Propiedades de conexión cargadas desde el archivo XML.

    // Constructor privado para evitar instanciación directa y cargar las propiedades de conexión desde el archivo XML.
    private ConnectionMariaDB() {
        properties = (ConnectionProperties) XMLManager.readXML(new ConnectionProperties(), FILE);
    }

    // Método estático para obtener una conexión a la base de datos.
    public static Connection getConnection() {
        // Si la instancia Singleton no ha sido creada, se inicializa.
        if (_instance == null) {
            _instance = new ConnectionMariaDB();
        }
        try {
            // Se retorna una nueva conexión usando las propiedades cargadas.
            return DriverManager.getConnection(_instance.properties.getURL(), _instance.properties.getUser(), _instance.properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el error en caso de fallo al conectar.
            return null; // Retorna null si no se puede establecer la conexión.
        }
    }

    // Método estático para cerrar una conexión existente.
    public static void closeConnection(Connection conn) {
        if (conn != null) { // Verifica si la conexión no es nula.
            try {
                conn.close(); // Intenta cerrar la conexión.
            } catch (SQLException e) {
                e.printStackTrace(); // Imprime el error si ocurre un problema al cerrar la conexión.
            }
        }
    }
}
