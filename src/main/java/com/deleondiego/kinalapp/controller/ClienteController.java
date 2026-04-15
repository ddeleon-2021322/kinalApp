package com.deleondiego.kinalapp.controller;

import com.deleondiego.kinalapp.entity.Cliente;
import com.deleondiego.kinalapp.service.IClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/gestion")
    public String mostrarGestion(Model model) {
        List<Cliente> clientes = clienteService.listarTodos();
        model.addAttribute("clientes", clientes);
        return "clientes"; // Nombre del HTML
    }

    @PostMapping("/guardar")
    public String guardarDesdeForm(@ModelAttribute Cliente cliente) {
        try {
            clienteService.guardar(cliente);
            return "redirect:/clientes/gestion";
        } catch (Exception e) {
            return "redirect:/clientes/gestion?error";
        }
    }

    @GetMapping("/editar/{dpi}")
    public String mostrarEditar(@PathVariable String dpi, Model model) {
        Cliente cliente = clienteService.buscarPorDPI(dpi).orElse(null);
        if (cliente == null) {
            return "redirect:/clientes/gestion?error=no_encontrado";
        }
        model.addAttribute("cliente", cliente);
        return "editarcliente";
    }

    @GetMapping("/eliminar/{dpi}")
    public String eliminar(@PathVariable String dpi) {
        clienteService.eliminar(dpi);
        return "redirect:/clientes/gestion";
    }
}