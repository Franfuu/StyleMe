package com.github.Franfuu.model.dao;

public class PeluqueroDAO {
private static final String FINDALL = "SELECT * FROM peluquero";
private static final String FINDBYID = "SELECT * FROM peluquero WHERE Id = ?";
private static final String INSERT = "INSERT INTO peluquero (Nombre, Apellido, Email, Telefono, Direccion, Password) VALUES (?,?,?,?,?,?,?)";
private static final String UPDATE = "UPDATE peluquero SET Nombre=?, Apellido=?, Email=?, Telefono=?, Direccion=?, Password=? WHERE Id=?";
private static final String DELETE = "DELETE FROM peluquero WHERE Id=?";

public PeluqueroDAO() {

}

}
