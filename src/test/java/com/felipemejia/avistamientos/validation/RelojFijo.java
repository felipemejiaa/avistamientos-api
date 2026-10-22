package com.felipemejia.avistamientos.validation;

import java.time.LocalDate;

public class RelojFijo implements Reloj {
    private final LocalDate fecha;

    public RelojFijo(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public LocalDate hoy() {
        return fecha;
    }
}