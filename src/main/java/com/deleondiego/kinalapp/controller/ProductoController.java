package com.deleondiego.kinalapp.controller;

import com.deleondiego.kinalapp.entity.Producto;
import com.deleondiego.kinalapp.service.IProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    // 1. RUTA PRINCIPAL: Ahora /productos y /productos/gestion cargan el HTML
    @GetMapping({"", "/", "/gestion"})
    public String mostrarGestion(Model model) {
        List<Producto> productos = productoService.listarProductos();
        model.addAttribute("productos", productos);
        return "productos";
    }

    // 2. GUARDAR / ACTUALIZAR: Procesa el formulario y redirecciona
    @PostMapping("/guardar")
    public String guardarDesdeForm(@ModelAttribute Producto producto) {
        try {
            productoService.guardar(producto);
            return "redirect:/productos/gestion";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/productos/gestion?error";
        }
    }

    // 3. EDITAR: Carga el formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.buscarPorCodigo(id.intValue()).orElse(null);

        if (producto == null) {
            return "redirect:/productos/gestion?error=no_encontrado";
        }

        model.addAttribute("producto", producto);
        return "editarproducto";
    }

    // 4. ELIMINAR: Borra y regresa a la tabla
    @GetMapping("/eliminar/{id}")
    public String eliminarDesdeWeb(@PathVariable Long id) {
        try {
            productoService.eliminar(id.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/productos/gestion";
    }

    // 5. API OPCIONAL: Por si necesitas ver el JSON (ahora en /productos/api)
    @GetMapping("/api")
    @ResponseBody
    public List<Producto> listarApi() {
        return productoService.listarProductos();
    }
}