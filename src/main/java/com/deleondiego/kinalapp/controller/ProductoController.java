package com.deleondiego.kinalapp.controller;

import com.deleondiego.kinalapp.entity.Producto;
import com.deleondiego.kinalapp.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{codigoProducto}")
    public ResponseEntity<Producto> buscarPorCodigo(@PathVariable int codigoProducto) {
        return productoService.buscarPorCodigo(codigoProducto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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

    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.buscarPorCodigo(id.intValue()).orElse(null);

        if (producto == null) {
            return "redirect:/productos/gestion?error=no_encontrado";
        }

        model.addAttribute("producto", producto);

        return "editarproducto";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDesdeWeb(@PathVariable Long id) {
        try {
            productoService.eliminar(id.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/productos/gestion";
    }
    @GetMapping("/gestion")
    public String mostrarGestion(Model model) {
        List<Producto> productos = productoService.listarProductos();
        model.addAttribute("productos", productos);
        return "productos"; // Nombre de tu archivo .html
    }
}