package com.github.Franfuu.model.entity;

import java.util.Objects;

public class Peluquero {
    private int Id;
    private String Nombre;
    private String Apellido;
    private String Telefono;
    private String Correo;
    private String Especialidad;
    private String Contraseña;

    public Peluquero() {
    }

    public Peluquero(int id, String nombre, String apellido, String telefono, String correo, String especialidad, String contraseña) {
        Id = id;
        Nombre = nombre;
        Apellido = apellido;
        Telefono = telefono;
        Correo = correo;
        Especialidad = especialidad;
        Contraseña = contraseña;
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

    public String getEspecialidad() {
        return Especialidad;
    }

    public void setEspecialidad(String especialidad) {
        Especialidad = especialidad;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Peluquero)) return false;
        Peluquero peluquero = (Peluquero) o;
        return getId() == peluquero.getId() && Objects.equals(getNombre(), peluquero.getNombre()) && Objects.equals(getApellido(), peluquero.getApellido()) && Objects.equals(getTelefono(), peluquero.getTelefono()) && Objects.equals(getCorreo(), peluquero.getCorreo()) && Objects.equals(getEspecialidad(), peluquero.getEspecialidad()) && Objects.equals(getContraseña(), peluquero.getContraseña());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getApellido(), getTelefono(), getCorreo(), getEspecialidad(), getContraseña());
    }

    @Override
    public String toString() {
        return "Peluquero{" +
                "Id=" + Id +
                ", Nombre='" + Nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Especialidad='" + Especialidad + '\'' +
                ", Contraseña='" + Contraseña + '\'' +
                '}';
    }
}
