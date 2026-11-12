package com.felipemejia.avistamientos.infraestructura.salida;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvistamientoRepositorioJpa extends JpaRepository<AvistamientoEntidad, Integer> {
}