package com.github.Franfuu.model.entity;

import java.util.Objects;

public class Cita {
    private int Id;
    private String Fecha;
    private String Hora;
    private String Observacion;
    private int IdCliente;
    private int IdPeluquero;

    public Cita() {
    }

    public Cita(String fecha, String hora, String observacion, int idCliente, int idPeluquero) {
        Fecha = fecha;
        Hora = hora;
        Observacion = observacion;
        IdCliente = idCliente;
        IdPeluquero = idPeluquero;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        this.Hora = hora;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        this.Observacion = observacion;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        this.IdCliente = idCliente;
    }

    public int getIdPeluquero() {
        return IdPeluquero;
    }

    public void setIdPeluquero(int idPeluquero) {
        this.IdPeluquero = idPeluquero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cita)) return false;
        Cita cita = (Cita) o;
        return getId() == cita.getId() && getIdCliente() == cita.getIdCliente() && getIdPeluquero() == cita.getIdPeluquero() && Objects.equals(getFecha(), cita.getFecha()) && Objects.equals(getHora(), cita.getHora()) && Objects.equals(getObservacion(), cita.getObservacion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFecha(), getHora(), getObservacion(), getIdCliente(), getIdPeluquero());
    }

    @Override
    public String toString() {
        return "Cita{" +
                "Id=" + Id +
                ", Fecha='" + Fecha + '\'' +
                ", Hora='" + Hora + '\'' +
                ", Observacion='" + Observacion + '\'' +
                ", IdCliente=" + IdCliente +
                ", IdPeluquero=" + IdPeluquero +
                '}';
    }
}