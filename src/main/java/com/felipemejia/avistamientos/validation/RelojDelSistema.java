package com.felipemejia.avistamientos.validation;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class RelojDelSistema implements Reloj {
    @Override
    public LocalDate hoy() {
        return LocalDate.now();
    }
}