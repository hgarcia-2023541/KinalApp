package com.herbertgarcia.kinalapp.controller;

import com.herbertgarcia.kinalapp.entity.Producto;
import com.herbertgarcia.kinalapp.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductoViewController {

    private final ProductoService productoService;

    public ProductoViewController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/productos")
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("titulo", "Listado de Productos");
        return "productos/lista";
    }

    @GetMapping("/productos/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Nuevo Producto");
        return "productos/formulario";
    }

    @GetMapping("/productos/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        productoService.buscarPorId(id).ifPresent(producto -> model.addAttribute("producto", producto));
        model.addAttribute("titulo", "Editar Producto");
        return "productos/formulario";
    }

    @PostMapping("/productos/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/productos";
    }
}