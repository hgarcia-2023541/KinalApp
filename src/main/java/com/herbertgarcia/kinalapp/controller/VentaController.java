package com.herbertgarcia.kinalapp.controller;

import com.herbertgarcia.kinalapp.entity.Venta;
import com.herbertgarcia.kinalapp.service.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listar() {
        return ResponseEntity.ok(ventaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable Long id) {
        return ventaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Venta>> listarActivos() {
        return ResponseEntity.ok(ventaService.listarActivos());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta) {
        try {
            Venta nueva = ventaService.guardar(venta);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Venta venta) {
        try {
            if (!ventaService.existePorId(id)) {
                return ResponseEntity.notFound().build();
            }
            Venta actualizada = ventaService.actualizar(id, venta);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            if (!ventaService.existePorId(id)) {
                return ResponseEntity.notFound().build();
            }
            ventaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}