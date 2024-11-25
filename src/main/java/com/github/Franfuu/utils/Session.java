package com.github.Franfuu.utils;

import com.github.Franfuu.model.entity.Cliente;

public class Session {

    private static Session _instance;
    private static Cliente userLogged;

    private Session() {

    }

    public static Session getInstance() {
        if (_instance == null) {
            _instance = new Session();
            _instance.logIn(userLogged);
        }
        return _instance;
    }

    public void logIn(Cliente client) {
        userLogged = client;
    }

    public Cliente getUserLogged() {
        return userLogged;
    }

    public void logOut() {
        userLogged = null;
    }
}
