package com.github.Franfuu.utils;

import com.github.Franfuu.model.entity.Cliente;

public class Session {

    // Instancia única de la sesión
    private static Session _instance;
    // Cliente actualmente logueado
    private static Cliente userLogged;

    // Constructor privado para evitar instanciación externa
    private Session() {

    }

    // Obtiene la instancia única de la sesión
    public static Session getInstance() {
        if (_instance == null) {
            _instance = new Session();
            _instance.logIn(userLogged);
        }
        return _instance;
    }

    // Inicia sesión con un cliente
    public void logIn(Cliente client) {
        userLogged = client;
    }

    // Obtiene el cliente actualmente logueado
    public Cliente getUserLogged() {
        return userLogged;
    }

    // Cierra la sesión
    public void logOut() {
        userLogged = null;
    }
}