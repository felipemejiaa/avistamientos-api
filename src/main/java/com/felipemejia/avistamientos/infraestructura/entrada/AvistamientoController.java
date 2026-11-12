package com.felipemejia.avistamientos.infraestructura.entrada;

import com.felipemejia.avistamientos.dominio.Avistamiento;
import com.felipemejia.avistamientos.aplicacion.ServicioDeAvistamientos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/avistamientos")
public class AvistamientoController {

    private final ServicioDeAvistamientos servicio;

    public AvistamientoController(ServicioDeAvistamientos servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<Avistamiento> listar() {
        return servicio.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avistamiento> obtener(@PathVariable int id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Avistamiento avistamiento) {
        String error = servicio.validar(avistamiento);
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }
        Avistamiento creado = servicio.crear(avistamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Avistamiento avistamiento) {
        if (servicio.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        String error = servicio.validar(avistamiento);
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }
        Avistamiento actualizado = servicio.actualizar(id, avistamiento);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (servicio.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", "fecha: formato inválido, use AAAA-MM-DD"));
    }
}