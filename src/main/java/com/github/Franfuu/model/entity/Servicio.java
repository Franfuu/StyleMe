package com.github.Franfuu.model.entity;

import java.util.Objects;

public class Servicio {
    private int Id;
    private String Nombre;
    private String Precio;

    public Servicio() {
    }

    public Servicio( String nombre, String precio) {
        Nombre = nombre;
        Precio = precio;
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

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Servicio)) return false;
        Servicio servicio = (Servicio) o;
        return getId() == servicio.getId() && Objects.equals(getNombre(), servicio.getNombre()) && Objects.equals(getPrecio(), servicio.getPrecio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getPrecio());
    }

    @Override
    public String toString() {
        return Nombre +
                ", " + Precio + "€";
    }
}
