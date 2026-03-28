package com.herbertgarcia.kinalapp.service;

import com.herbertgarcia.kinalapp.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    //Interfaz: Es un contrato que dice QUÉ métodos deben tener
    //cualquier servicio de Clientes, no tiene
    //implementacion, solo la definicion de los métodos

    //Metodo que devuelve una lista de todos los clientes
    List<Cliente> listarTodos();
    //List<Cliente> su función es devolver una lista
    //de objetos de la entidad Clientes

    //Metodo que guarda un Cliente en la BD
    Cliente guardar(Cliente cliente);
    //Parámetros - Recibe un objeto de tipo Cliente con los datos a guardar

    //Optional - Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Cliente> buscarPorDPI(String dpi);

    //Metodo que actualiza un Cliente
    Cliente actualizar(String dpi, Cliente cliente);
    //Parámetros - dpi: DPI del Cliente a actualizar
    //Cliente cliente: Objeto con los datos nuevos
    //Retorna un objeto de tipo Cliente ya actualizado

    //Metodo de tipo void para eliminar a un Cliente
    //void: no retorna ningun dato
    //Elimina un Cliente por su DPI
    void eliminar(String dpi);

    //boolean - Retorna true si existe, false si no existe
    boolean existePorDPI(String dpi);

    //Metodo que devuelve una lista de los clientes activos
    List<Cliente> listarActivos();
}
