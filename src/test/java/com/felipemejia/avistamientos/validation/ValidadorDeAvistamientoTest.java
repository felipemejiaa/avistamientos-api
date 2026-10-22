package com.felipemejia.avistamientos.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import com.felipemejia.avistamientos.model.Avistamiento;

class ValidadorDeAvistamientoTest {

    private static final LocalDate HOY = LocalDate.of(2026, 10, 21);
    private final ValidadorDeAvistamiento validador = new ValidadorDeAvistamiento(new RelojFijo(HOY));

    private Avistamiento conEspecieYFecha(String especie, LocalDate fecha) {
        return new Avistamiento(especie, "Chingaza", fecha, "Felipe");
    }

    @Test
    void rechazaEspecie_cuandoTiene2Caracteres() {
        // Arrange
        Avistamiento a = conEspecieYFecha("ab", HOY);
        // Act
        String error = validador.validar(a);
        // Assert
        assertEquals("especie: debe tener entre 3 y 50 caracteres", error);
    }

    @Test
    void aceptaEspecie_cuandoTiene3Caracteres() {
        // Arrange
        Avistamiento a = conEspecieYFecha("abc", HOY);
        // Act
        String error = validador.validar(a);
        // Assert
        assertNull(error);
    }

    @Test
    void aceptaEspecie_cuandoTiene50Caracteres() {
        // Arrange
        Avistamiento a = conEspecieYFecha("a".repeat(50), HOY);
        // Act
        String error = validador.validar(a);
        // Assert
        assertNull(error);
    }

    @Test
    void rechazaEspecie_cuandoTiene51Caracteres() {
        // Arrange
        Avistamiento a = conEspecieYFecha("a".repeat(51), HOY);
        // Act
        String error = validador.validar(a);
        // Assert
        assertEquals("especie: debe tener entre 3 y 50 caracteres", error);
    }

    @Test
    void rechazaEspecie_cuandoSoloTieneEspacios() {
        // Arrange
        Avistamiento a = conEspecieYFecha("     ", HOY);
        // Act
        String error = validador.validar(a);
        // Assert
        assertEquals("especie: debe tener entre 3 y 50 caracteres", error);
    }

    @Test
    void rechazaEspecie_cuandoEsNula() {
        // Arrange
        Avistamiento a = conEspecieYFecha(null, HOY);
        // Act
        String error = validador.validar(a);
        // Assert
        assertEquals("especie: debe tener entre 3 y 50 caracteres", error);
    }

    @Test
    void aceptaFecha_cuandoEsHoy() {
        // Arrange
        Avistamiento a = conEspecieYFecha("Águila", HOY);
        // Act
        String error = validador.validar(a);
        // Assert
        assertNull(error);
    }

    @Test
    void rechazaFecha_cuandoEsManana() {
        // Arrange
        Avistamiento a = conEspecieYFecha("Águila", HOY.plusDays(1));
        // Act
        String error = validador.validar(a);
        // Assert
        assertEquals("fecha: no puede ser posterior a hoy", error);
    }

    @Test
    void rechazaFecha_cuandoEsNula() {
        // Arrange
        Avistamiento a = conEspecieYFecha("Águila", null);
        // Act
        String error = validador.validar(a);
        // Assert
        assertEquals("fecha: es obligatoria con formato AAAA-MM-DD", error);
    }

    @Test
    void rechazaLugar_cuandoEstaVacio() {
        // Arrange
        Avistamiento a = new Avistamiento("Águila", "", HOY, "Felipe");
        // Act
        String error = validador.validar(a);
        // Assert
        assertEquals("lugar: no puede estar vacío", error);
    }
}