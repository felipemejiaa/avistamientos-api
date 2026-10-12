package com.felipemejia.avistamientos.repository;

import com.felipemejia.avistamientos.model.Avistamiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvistamientoRepository extends JpaRepository<Avistamiento, Integer> {
}