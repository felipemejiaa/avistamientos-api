package com.felipemejia.avistamientos.infraestructura.salida;

import com.felipemejia.avistamientos.dominio.Reloj;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RelojDelSistema implements Reloj {
    @Override
    public LocalDate hoy() {
        return LocalDate.now();
    }
}