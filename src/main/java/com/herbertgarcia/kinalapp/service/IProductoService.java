package com.herbertgarcia.kinalapp.service;

import com.herbertgarcia.kinalapp.entity.Producto;
import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<Producto> listarTodos();

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorId(Long id);

    Producto actualizar(Long id, Producto producto);

    void eliminar(Long id);

    boolean existePorId(Long id);

    List<Producto> listarActivos();
}