package com.felipemejia.avistamientos.infraestructura.salida;

import jakarta.persistence.*;

@Entity(name = "Avistamiento")
@Table(name = "AVISTAMIENTO")
public class AvistamientoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String especie;
    private String lugar;
    private java.time.LocalDate fecha;
    private String observador;

    public AvistamientoEntidad() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public java.time.LocalDate getFecha() { return fecha; }
    public void setFecha(java.time.LocalDate fecha) { this.fecha = fecha; }
    public String getObservador() { return observador; }
    public void setObservador(String observador) { this.observador = observador; }
}