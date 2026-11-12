package com.felipemejia.avistamientos.aplicacion;

import com.felipemejia.avistamientos.dominio.Avistamiento;
import com.felipemejia.avistamientos.dominio.RepositorioDeAvistamientos;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioDeAvistamientos {

    private final RepositorioDeAvistamientos repositorio;
    private final ValidadorDeAvistamiento validador;

    public ServicioDeAvistamientos(RepositorioDeAvistamientos repositorio,
                                   ValidadorDeAvistamiento validador) {
        this.repositorio = repositorio;
        this.validador = validador;
    }

    public List<Avistamiento> listarTodos() {
        return repositorio.listarTodos();
    }

    public Optional<Avistamiento> buscarPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    public Avistamiento crear(Avistamiento avistamiento) {
        avistamiento.setId(0);
        return repositorio.guardar(avistamiento);
    }

    public Avistamiento actualizar(int id, Avistamiento avistamiento) {
        avistamiento.setId(id);
        return repositorio.guardar(avistamiento);
    }

    public void eliminar(int id) {
        repositorio.eliminar(id);
    }

    public String validar(Avistamiento avistamiento) {
        return validador.validar(avistamiento);
    }
}