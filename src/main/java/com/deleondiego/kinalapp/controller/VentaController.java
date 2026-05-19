package com.deleondiego.kinalapp.controller;

import com.deleondiego.kinalapp.entity.Venta;
import com.deleondiego.kinalapp.service.IVentaService;
import com.deleondiego.kinalapp.service.IClienteService; // Necesario
import com.deleondiego.kinalapp.service.IUsuarioService; // Necesario
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Asegúrate de que NO diga @RestController
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

    // Cambiamos esta ruta para que sea la principal y no choque
    @GetMapping("/gestion")
    public String listarVentas(Model model) {
        model.addAttribute("ventas", ventaService.listarVentas());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "ventas"; // Esto carga el HTML
    }

    // Para buscar por ID, le agregamos /buscar/ para que no choque con /gestion
    @GetMapping("/buscar/{codigoVenta}")
    @ResponseBody // Para que devuelva JSON si lo necesitas
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