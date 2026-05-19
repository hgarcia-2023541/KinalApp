package com.herbertgarcia.kinalapp.controller;

import com.herbertgarcia.kinalapp.entity.Venta;
import com.herbertgarcia.kinalapp.service.ClienteService;
import com.herbertgarcia.kinalapp.service.UsuarioService;
import com.herbertgarcia.kinalapp.service.VentaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class VentaViewController {

    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    public VentaViewController(VentaService ventaService, ClienteService clienteService, UsuarioService usuarioService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/ventas")
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarTodos());
        model.addAttribute("titulo", "Listado de Ventas");
        return "ventas/lista";
    }

    @GetMapping("/ventas/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("titulo", "Nueva Venta");
        model.addAttribute("clientes", clienteService.listarActivos());
        model.addAttribute("usuarios", usuarioService.listarActivos());
        return "ventas/formulario";
    }

    @GetMapping("/ventas/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ventaService.buscarPorId(id).ifPresent(venta -> model.addAttribute("venta", venta));
        model.addAttribute("titulo", "Editar Venta");
        model.addAttribute("clientes", clienteService.listarActivos());
        model.addAttribute("usuarios", usuarioService.listarActivos());
        return "ventas/formulario";
    }

    @PostMapping("/ventas/guardar")
    public String guardar(
            @RequestParam(required = false) Long codigoVenta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaVenta,
            @RequestParam String dpiCliente,
            @RequestParam Long codigoUsuario,
            @RequestParam(required = false) Long estado) {

        Venta venta = new Venta();
        if (codigoVenta != null) venta.setCodigoVenta(codigoVenta);
        venta.setFechaVenta(fechaVenta);
        venta.setEstado(estado != null ? estado : 1L);
        venta.setTotal(new BigDecimal("0.00"));

        clienteService.buscarPorDPI(dpiCliente)
                .ifPresent(venta::setCliente);
        usuarioService.buscarPorId(codigoUsuario)
                .ifPresent(venta::setUsuario);

        if (codigoVenta != null) {
            ventaService.actualizar(codigoVenta, venta);
        } else {
            ventaService.guardar(venta);
        }
        return "redirect:/ventas";
    }

    @GetMapping("/ventas/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
        return "redirect:/ventas";
    }
}