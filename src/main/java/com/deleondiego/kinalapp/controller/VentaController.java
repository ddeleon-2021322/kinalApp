package com.deleondiego.kinalapp.controller;

import com.deleondiego.kinalapp.entity.Venta;
import com.deleondiego.kinalapp.service.IVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listar() {
        List<Venta> ventas = ventaService.listarVentas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{codigoVenta}")
    public ResponseEntity<Venta> buscarPorCodigo(@PathVariable Long codigoVenta) {
        return ventaService.buscarPorCodigoVenta(codigoVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta) {
        try {
            Venta nuevaVenta = ventaService.guardar(venta);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoVenta) {
        try {
            if (!ventaService.existePorCodigoVenta(codigoVenta)) {
                return ResponseEntity.notFound().build();
            }
            ventaService.eliminar(codigoVenta);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigoVenta}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigoVenta, @RequestBody Venta venta) {
        try {
            if (!ventaService.existePorCodigoVenta(codigoVenta)) {
                return ResponseEntity.notFound().build();
            }
            Venta ventaActualizada = ventaService.actualizar(codigoVenta, venta);
            return ResponseEntity.ok(ventaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}