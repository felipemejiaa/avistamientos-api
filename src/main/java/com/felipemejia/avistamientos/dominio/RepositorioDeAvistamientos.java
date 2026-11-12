package com.felipemejia.avistamientos.dominio;

import java.util.List;
import java.util.Optional;

public interface RepositorioDeAvistamientos {
    Avistamiento guardar(Avistamiento avistamiento);
    Optional<Avistamiento> buscarPorId(int id);
    List<Avistamiento> listarTodos();
    void eliminar(int id);
}