package com.herbertgarcia.kinalapp.controller;

import com.herbertgarcia.kinalapp.entity.Producto;
import com.herbertgarcia.kinalapp.service.ProductoService;
import jakarta.servlet.http.HttpSession;
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
    public String listar(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("titulo", "Listado de Productos");
        return "productos/lista";
    }

    @GetMapping("/productos/nuevo")
    public String nuevo(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Nuevo Producto");
        return "productos/formulario";
    }

    @GetMapping("/productos/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        productoService.buscarPorId(id).ifPresent(producto -> model.addAttribute("producto", producto));
        model.addAttribute("titulo", "Editar Producto");
        return "productos/formulario";
    }

    @PostMapping("/productos/guardar")
    public String guardar(@ModelAttribute Producto producto, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        productoService.eliminar(id);
        return "redirect:/productos";
    }
}