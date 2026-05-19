package com.deleondiego.kinalapp.controller;

import com.deleondiego.kinalapp.entity.Venta;
import com.deleondiego.kinalapp.service.IVentaService;
import com.deleondiego.kinalapp.service.IClienteService;
import com.deleondiego.kinalapp.service.IUsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final IVentaService ventaService;
    private final IClienteService clienteService;
    private final IUsuarioService usuarioService;

    public VentaController(IVentaService ventaService, IClienteService clienteService, IUsuarioService usuarioService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/gestion")
    public String listarVentas(Authentication authentication, Model model) {
        boolean esAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("esAdmin", esAdmin);

        model.addAttribute("ventas", ventaService.listarVentas());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "ventas";
    }

    @GetMapping("/buscar/{codigoVenta}")
    @ResponseBody
    public Venta buscarPorCodigo(@PathVariable Long codigoVenta) {
        return ventaService.buscarPorCodigoVenta(codigoVenta).orElse(null);
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Venta venta) {
        ventaService.guardar(venta);
        return "redirect:/ventas/gestion";
    }

    @GetMapping("/eliminar/{codigoVenta}")
    public String eliminar(@PathVariable Long codigoVenta) {
        ventaService.eliminar(codigoVenta);
        return "redirect:/ventas/gestion";
    }
}