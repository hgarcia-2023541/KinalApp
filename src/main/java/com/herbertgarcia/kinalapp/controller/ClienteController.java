package com.herbertgarcia.kinalapp.controller;

import com.herbertgarcia.kinalapp.entity.Cliente;
import com.herbertgarcia.kinalapp.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")  // ← AGREGAR /api/
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{dpi}")
    public ResponseEntity<Cliente> buscarPorDPI(@PathVariable String dpi) {
        return clienteService.buscarPorDPI(dpi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteService.guardar(cliente);
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable String dpi) {
        try {
            if (!clienteService.existePorDPI(dpi)) {
                return ResponseEntity.notFound().build();
            }
            clienteService.eliminar(dpi);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Cliente>> listarActivos() {
        return ResponseEntity.ok(clienteService.listarActivos());
    }

    @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizar(@PathVariable String dpi, @RequestBody Cliente cliente) {
        try {
            if (!clienteService.existePorDPI(dpi)) {
                return ResponseEntity.notFound().build();
            }
            Cliente clienteActualizado = clienteService.actualizar(dpi, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}