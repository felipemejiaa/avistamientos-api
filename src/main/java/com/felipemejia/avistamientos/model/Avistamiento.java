package com.felipemejia.avistamientos.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Avistamiento")
@Table(name = "avistamiento")
public class Avistamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "especie", nullable = false)
    private String especie;

    @Column(name = "lugar", nullable = false)
    private String lugar;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "observador", nullable = false)
    private String observador;

    public Avistamiento() {
    }

    public Avistamiento(String especie, String lugar, LocalDate fecha, String observador) {
        this.especie = especie;
        this.lugar = lugar;
        this.fecha = fecha;
        this.observador = observador;
    }

    public int getId() {
        return id;
    }

    public String getEspecie() {
        return especie;
    }

    public String getLugar() {
        return lugar;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getObservador() {
        return observador;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setObservador(String observador) {
        this.observador = observador;
    }

}