package com.github.Franfuu.model.entity;

public class ControlSesion {
    private static ControlSesion instance;
    private int loggedInClienteId;

    private ControlSesion() {
        // Constructor privado para el patrón Singleton
    }

    public static ControlSesion getInstance() {
        if (instance == null) {
            instance = new ControlSesion();
        }
        return instance;
    }

    public int getLoggedInClienteId() {
        return loggedInClienteId;
    }

    public void setLoggedInClienteId(int clienteId) {
        this.loggedInClienteId = clienteId;
    }
}
