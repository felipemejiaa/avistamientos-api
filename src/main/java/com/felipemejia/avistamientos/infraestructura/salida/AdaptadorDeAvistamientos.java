package com.felipemejia.avistamientos.infraestructura.salida;

import com.felipemejia.avistamientos.dominio.Avistamiento;
import com.felipemejia.avistamientos.dominio.RepositorioDeAvistamientos;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AdaptadorDeAvistamientos implements RepositorioDeAvistamientos {

    private final AvistamientoRepositorioJpa jpa;

    public AdaptadorDeAvistamientos(AvistamientoRepositorioJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public Avistamiento guardar(Avistamiento a) {
        AvistamientoEntidad entidad = aEntidad(a);
        AvistamientoEntidad guardada = jpa.save(entidad);
        return aDominio(guardada);
    }

    @Override
    public Optional<Avistamiento> buscarPorId(int id) {
        return jpa.findById(id).map(this::aDominio);
    }

    @Override
    public List<Avistamiento> listarTodos() {
        return jpa.findAll().stream().map(this::aDominio).toList();
    }

    @Override
    public void eliminar(int id) {
        jpa.deleteById(id);
    }

    private AvistamientoEntidad aEntidad(Avistamiento a) {
        AvistamientoEntidad e = new AvistamientoEntidad();
        e.setId(a.getId());
        e.setEspecie(a.getEspecie());
        e.setLugar(a.getLugar());
        e.setFecha(a.getFecha());
        e.setObservador(a.getObservador());
        return e;
    }

    private Avistamiento aDominio(AvistamientoEntidad e) {
        Avistamiento a = new Avistamiento(e.getEspecie(), e.getLugar(), e.getFecha(), e.getObservador());
        a.setId(e.getId());
        return a;
    }
}