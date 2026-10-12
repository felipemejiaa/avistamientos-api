package com.felipemejia.avistamientos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.felipemejia.avistamientos.model.Avistamiento;
import com.felipemejia.avistamientos.repository.AvistamientoRepository;

@RestController
@RequestMapping("/avistamientos")
public class AvistamientoController {

    @Autowired
    private AvistamientoRepository repository;


    @GetMapping
    public ResponseEntity<List<Avistamiento>> listar() {
        List<Avistamiento> lista = repository.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Avistamiento> obtener(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Avistamiento encontrado = repository.findById(id).get();
        return new ResponseEntity<>(encontrado, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Avistamiento> crear(@RequestBody Avistamiento nuevo) {
        if (faltanDatos(nuevo)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        nuevo.setId(0);
        Avistamiento guardado = repository.save(nuevo);
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Avistamiento> actualizar(@PathVariable int id, @RequestBody Avistamiento datos) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (faltanDatos(datos)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        datos.setId(id);
        Avistamiento actualizado = repository.save(datos);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean faltanDatos(Avistamiento a) {
        return a.getEspecie() == null || a.getEspecie().isBlank()
                || a.getLugar() == null || a.getLugar().isBlank()
                || a.getFecha() == null || a.getObservador() == null || a.getObservador().isBlank();
    }
}