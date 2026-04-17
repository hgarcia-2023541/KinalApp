package com.herbertgarcia.kinalapp.controller;

import com.herbertgarcia.kinalapp.entity.DetalleVenta;
import com.herbertgarcia.kinalapp.service.DetalleVentaService;
import com.herbertgarcia.kinalapp.service.ProductoService;
import com.herbertgarcia.kinalapp.service.VentaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class DetalleVentaViewController {

    private final DetalleVentaService detalleVentaService;
    private final ProductoService productoService;
    private final VentaService ventaService;

    public DetalleVentaViewController(DetalleVentaService detalleVentaService, ProductoService productoService, VentaService ventaService) {
        this.detalleVentaService = detalleVentaService;
        this.productoService = productoService;
        this.ventaService = ventaService;
    }

    @GetMapping("/detalle-ventas")
    public String listar(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("detalles", detalleVentaService.listarTodos());
        model.addAttribute("titulo", "Listado de Detalles de Venta");
        return "detalle-ventas/lista";
    }

    @GetMapping("/detalle-ventas/nuevo")
    public String nuevo(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("detalle", new DetalleVenta());
        model.addAttribute("titulo", "Nuevo Detalle de Venta");
        model.addAttribute("productos", productoService.listarActivos());
        model.addAttribute("ventas", ventaService.listarActivos());
        return "detalle-ventas/formulario";
    }

    @GetMapping("/detalle-ventas/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        detalleVentaService.buscarPorId(id).ifPresent(detalle -> model.addAttribute("detalle", detalle));
        model.addAttribute("titulo", "Editar Detalle de Venta");
        model.addAttribute("productos", productoService.listarActivos());
        model.addAttribute("ventas", ventaService.listarActivos());
        return "detalle-ventas/formulario";
    }

    // ← CORREGIDO: recibe IDs por separado
    @PostMapping("/detalle-ventas/guardar")
    public String guardar(
            @RequestParam(required = false) Long codigoDetalleVenta,
            @RequestParam Long cantidad,
            @RequestParam BigDecimal precioUnitario,
            @RequestParam Long codigoVenta,
            @RequestParam Long codigoProducto,
            HttpSession session) {

        if (session.getAttribute("usuario") == null) return "redirect:/login";

        DetalleVenta detalle = new DetalleVenta();
        if (codigoDetalleVenta != null) detalle.setCodigoDetalleVenta(codigoDetalleVenta);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);

        ventaService.buscarPorId(codigoVenta)
                .ifPresent(detalle::setVenta);
        productoService.buscarPorId(codigoProducto)
                .ifPresent(detalle::setProducto);

        if (codigoDetalleVenta != null) {
            detalleVentaService.actualizar(codigoDetalleVenta, detalle);
        } else {
            detalleVentaService.guardar(detalle);
        }
        return "redirect:/detalle-ventas";
    }

    @GetMapping("/detalle-ventas/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        detalleVentaService.eliminar(id);
        return "redirect:/detalle-ventas";
    }
}