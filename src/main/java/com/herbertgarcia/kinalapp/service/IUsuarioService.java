package com.herbertgarcia.kinalapp.service;

import com.herbertgarcia.kinalapp.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> listarTodos();

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorId(Long id);

    Usuario actualizar(Long id, Usuario usuario);

    void eliminar(Long id);

    boolean existePorId(Long id);

    List<Usuario> listarActivos();
}