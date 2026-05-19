package com.deleondiego.kinalapp.controller;

import com.deleondiego.kinalapp.entity.DetalleVenta;
import com.deleondiego.kinalapp.service.IDetalleVentaService;
import com.deleondiego.kinalapp.service.IProductoService;
import com.deleondiego.kinalapp.service.IVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/detalles-ventas")
public class DetalleVentaController {

    private final IDetalleVentaService detalleVentaService;
    private final IVentaService ventaService;
    private final IProductoService productoService;

    public DetalleVentaController(IDetalleVentaService detalleVentaService, IVentaService ventaService, IProductoService productoService) {
        this.detalleVentaService = detalleVentaService;
        this.ventaService = ventaService;
        this.productoService = productoService;
    }

    // Ruta para el Menú Principal
    @GetMapping("/menuprincipal")
    public String mostrarMenu() {
        return "menuprincipal";
    }

    @GetMapping("/gestion")
    public String listar(Model model) {
        model.addAttribute("detalles", detalleVentaService.listarDetalles());
        model.addAttribute("ventas", ventaService.listarVentas());
        model.addAttribute("productos", productoService.listarProductos());
        return "detalle-venta";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute DetalleVenta detalleVenta) {
        if (detalleVenta.getCantidad() != null && detalleVenta.getPrecioUnitario() != null) {
            BigDecimal cantidad = new BigDecimal(detalleVenta.getCantidad());
            detalleVenta.setSubtotal(detalleVenta.getPrecioUnitario().multiply(cantidad));
        }
        detalleVentaService.guardar(detalleVenta);
        return "redirect:/detalles-ventas/gestion";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        DetalleVenta detalle = detalleVentaService.buscarPorCodigoDetalleVenta(id).orElse(null);
        if (detalle == null) return "redirect:/detalles-ventas/gestion";

        model.addAttribute("detalleVenta", detalle);
        model.addAttribute("ventas", ventaService.listarVentas());
        model.addAttribute("productos", productoService.listarProductos());
        return "editar-detalle";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute("detalleVenta") DetalleVenta detalleVenta) {
        if (detalleVenta.getCantidad() != null && detalleVenta.getPrecioUnitario() != null) {
            BigDecimal cantidad = new BigDecimal(detalleVenta.getCantidad());
            detalleVenta.setSubtotal(detalleVenta.getPrecioUnitario().multiply(cantidad));
        }
        detalleVentaService.guardar(detalleVenta);
        return "redirect:/detalles-ventas/gestion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        detalleVentaService.eliminar(id);
        return "redirect:/detalles-ventas/gestion";
    }
}