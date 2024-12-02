package com.github.Franfuu.model.connection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="connection") // Define esta clase como el elemento raíz para XML.
@XmlAccessorType(XmlAccessType.FIELD) // Permite acceder a los campos directamente para la serialización.
public class ConnectionProperties implements Serializable {
    private static final long serialVersionUID = 1L; // Identificador para la serialización de la clase.
    private String server; // Dirección del servidor de la base de datos.
    private String port; // Puerto del servidor de la base de datos.
    private String database; // Nombre de la base de datos.
    private String user; // Usuario para la conexión a la base de datos.
    private String password; // Contraseña para la conexión a la base de datos.

    // Constructor con todos los parámetros, utilizado para inicializar las propiedades de conexión.
    public ConnectionProperties(String server, String port, String database, String user, String password) {
        this.server = server;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    // Constructor vacío, requerido para la serialización y deserialización automática (por ejemplo, JAXB).
    public ConnectionProperties() {
    }

    // Getters y setters para los atributos, permiten acceder y modificar las propiedades de conexión.

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Método toString que retorna una representación en cadena de las propiedades de conexión.
    @Override
    public String toString() {
        return "ConnectionProperties{" +
                "server='" + server + '\'' +
                ", port='" + port + '\'' +
                ", database='" + database + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    // Método para construir la URL JDBC en base a las propiedades actuales.
    public String getURL() {
        return "jdbc:mariadb://" + server + ":" + port + "/" + database; // URL de conexión a MariaDB.
    }
}
