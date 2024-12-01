package com.github.Franfuu.model.entity;

import com.github.Franfuu.model.dao.PeluqueroDAO;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cita  {
    private int Id;
    private String Fecha;
    private String Hora;
    private String Observacion;
    private int IdCliente;
    private int IdPeluquero;
    private Button acciones;
    private Button servicio;
    private Button eliminarServicio;
    private List<Servicio> servicios = new ArrayList<>();

    public Cita() {
    }

    public Cita(String fecha, String hora, String observacion, int idCliente, int idPeluquero) {
        Fecha = fecha;
        Hora = hora;
        Observacion = observacion;
        IdCliente = idCliente;
        IdPeluquero = idPeluquero;
    }


    public Button getEliminarServicio() {
        return eliminarServicio;
    }

    public void setEliminarServicio(Button eliminarServicio) {
        this.eliminarServicio = eliminarServicio;
    }

    public void addServicio(Servicio servicio) {
        this.servicios.add(servicio);
    }

    public Button getAcciones() {
        return acciones;
    }

    public void setAcciones(Button acciones) {
        this.acciones = acciones;
    }

    public Button getServicios() {
        return servicio;
    }

    public void setServicios(Button servicios) {
        this.servicio = servicios;
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