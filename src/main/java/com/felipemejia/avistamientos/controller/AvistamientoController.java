package com.felipemejia.avistamientos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import com.felipemejia.avistamientos.model.Avistamiento;
import com.felipemejia.avistamientos.repository.AvistamientoRepository;
import com.felipemejia.avistamientos.validation.ValidadorDeAvistamiento;

@RestController
@RequestMapping("/avistamientos")
public class AvistamientoController {

    @Autowired
    private AvistamientoRepository repository;

    @Autowired
    private ValidadorDeAvistamiento validador;

    @GetMapping
    public ResponseEntity<List<Avistamiento>> listar() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avistamiento> obtener(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Avistamiento nuevo) {
        String error = validador.validar(nuevo);
        if (error != null) {
            return new ResponseEntity<>(Map.of("error", error), HttpStatus.BAD_REQUEST);
        }
        nuevo.setId(0);
        return new ResponseEntity<>(repository.save(nuevo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Avistamiento datos) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String error = validador.validar(datos);
        if (error != null) {
            return new ResponseEntity<>(Map.of("error", error), HttpStatus.BAD_REQUEST);
        }
        datos.setId(id);
        return new ResponseEntity<>(repository.save(datos), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> jsonInvalido() {
        return new ResponseEntity<>(Map.of("error", "fecha: formato inválido, use AAAA-MM-DD"), HttpStatus.BAD_REQUEST);
    }
}