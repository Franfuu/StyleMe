package com.github.Franfuu.model.test;

import com.github.Franfuu.model.dao.ClienteDAO;
import com.github.Franfuu.model.dao.PeluqueroDAO;
import com.github.Franfuu.model.entity.Cliente;
import com.github.Franfuu.model.entity.Peluquero;

import java.sql.SQLException;

public class TestInsert {
    public static void main(String[] args) {
        Peluquero c = new Peluquero("Franci", "Furias", "123456789", "a@a.com", "Helicoptero de combate", "12345678");

            PeluqueroDAO.build().save(c);
    }
}
