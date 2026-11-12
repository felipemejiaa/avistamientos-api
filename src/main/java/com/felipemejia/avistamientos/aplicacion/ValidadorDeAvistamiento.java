package com.felipemejia.avistamientos.aplicacion;

import com.felipemejia.avistamientos.dominio.Avistamiento;
import com.felipemejia.avistamientos.dominio.Reloj;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDeAvistamiento {

    private final Reloj reloj;

    public ValidadorDeAvistamiento(Reloj reloj) {
        this.reloj = reloj;
    }

    public String validar(Avistamiento a) {
        String especie = a.getEspecie() == null ? null : a.getEspecie().strip();
        if (especie == null || especie.length() < 3 || especie.length() > 50) {
            return "especie: debe tener entre 3 y 50 caracteres";
        }
        if (a.getFecha() == null) {
            return "fecha: es obligatoria con formato AAAA-MM-DD";
        }
        if (a.getFecha().isAfter(reloj.hoy())) {
            return "fecha: no puede ser posterior a hoy";
        }
        if (estaVacio(a.getLugar())) {
            return "lugar: no puede estar vacío";
        }
        if (estaVacio(a.getObservador())) {
            return "observador: no puede estar vacío";
        }
        return null;
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }
}