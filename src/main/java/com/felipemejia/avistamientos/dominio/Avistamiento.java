package com.felipemejia.avistamientos.dominio;

import java.time.LocalDate;

public class Avistamiento {
    private int id;
    private String especie;
    private String lugar;
    private LocalDate fecha;
    private String observador;

    public Avistamiento() {}

    public Avistamiento(String especie, String lugar, LocalDate fecha, String observador) {
        this.especie = especie;
        this.lugar = lugar;
        this.fecha = fecha;
        this.observador = observador;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getObservador() { return observador; }
    public void setObservador(String observador) { this.observador = observador; }
}