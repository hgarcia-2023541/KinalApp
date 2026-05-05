package com.herbertgarcia.kinalapp.controller;

import com.herbertgarcia.kinalapp.entity.Cliente;
import com.herbertgarcia.kinalapp.service.ClienteService;
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
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("titulo", "Listado de Clientes");
        return "clientes/lista";
    }

    @GetMapping("/clientes/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente");
        return "clientes/formulario";
    }

    @GetMapping("/clientes/editar/{dpi}")
    public String editar(@PathVariable String dpi, Model model) {
        clienteService.buscarPorDPI(dpi).ifPresent(cliente -> model.addAttribute("cliente", cliente));
        model.addAttribute("titulo", "Editar Cliente");
        return "clientes/formulario";
    }

    @PostMapping("/clientes/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/eliminar/{dpi}")
    public String eliminar(@PathVariable String dpi) {
        clienteService.eliminar(dpi);
        return "redirect:/clientes";
    }
}