package com.github.Franfuu.model.test;

import com.github.Franfuu.model.dao.ClienteDAO;
import com.github.Franfuu.model.entity.Cliente;

import java.sql.SQLException;

public class TestInsert {
    public static void main(String[] args) {
        Cliente c = new Cliente("Francisco", "Furias", "123456789", "a@a.com", "Helicoptero de combate", "12345678");

            ClienteDAO.build().save(c);
    }
}
