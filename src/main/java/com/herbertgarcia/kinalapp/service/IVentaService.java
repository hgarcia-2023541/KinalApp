package com.herbertgarcia.kinalapp.service;

import com.herbertgarcia.kinalapp.entity.Venta;
import java.util.List;
import java.util.Optional;

public interface IVentaService {

    List<Venta> listarTodos();

    Venta guardar(Venta venta);

    Optional<Venta> buscarPorId(Long id);

    Venta actualizar(Long id, Venta venta);

    void eliminar(Long id);

    boolean existePorId(Long id);

    List<Venta> listarActivos();
}