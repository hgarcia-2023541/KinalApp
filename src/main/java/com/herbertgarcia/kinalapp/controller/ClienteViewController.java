package com.herbertgarcia.kinalapp.controller;

import com.herbertgarcia.kinalapp.entity.Cliente;
import com.herbertgarcia.kinalapp.service.ClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClienteViewController {

    private final ClienteService clienteService;

    public ClienteViewController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clientes")
    public String listar(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("titulo", "Listado de Clientes");
        return "clientes/lista";
    }

    @GetMapping("/clientes/nuevo")
    public String nuevo(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente");
        return "clientes/formulario";
    }

    @GetMapping("/clientes/editar/{dpi}")
    public String editar(@PathVariable String dpi, Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        clienteService.buscarPorDPI(dpi).ifPresent(cliente -> model.addAttribute("cliente", cliente));
        model.addAttribute("titulo", "Editar Cliente");
        return "clientes/formulario";
    }

    @PostMapping("/clientes/guardar")
    public String guardar(@ModelAttribute Cliente cliente, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/eliminar/{dpi}")
    public String eliminar(@PathVariable String dpi, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        clienteService.eliminar(dpi);
        return "redirect:/clientes";
    }
}