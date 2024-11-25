package com.github.Franfuu.model.entity;

import java.util.Objects;

public class Cliente {
    private int Id;
    private String Nombre;
    private String Apellido;
    private String Telefono;
    private String Correo;
    private String Genero;
    private String Contraseña;

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String telefono, String correo, String genero, String contrasena) {
        Nombre = nombre;
        Apellido = apellido;
        Telefono = telefono;
        Correo = correo;
        Genero = genero;
        Contraseña = contrasena;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contrasena) {
        Contraseña = contrasena;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return getId() == cliente.getId() && Objects.equals(getNombre(), cliente.getNombre()) && Objects.equals(getApellido(), cliente.getApellido()) && Objects.equals(getTelefono(), cliente.getTelefono()) && Objects.equals(getCorreo(), cliente.getCorreo()) && Objects.equals(getGenero(), cliente.getGenero()) && Objects.equals(getContraseña(), cliente.getContraseña());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getApellido(), getTelefono(), getCorreo(), getGenero(), getContraseña());
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Id=" + Id +
                ", Nombre='" + Nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Genero='" + Genero + '\'' +
                ", Contraseña='" + Contraseña + '\'' +
                '}';
    }
}
