package com.deleondiego.kinalapp.controller;

import com.deleondiego.kinalapp.entity.DetalleVenta;
import com.deleondiego.kinalapp.service.IDetalleVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles-ventas")
public class DetalleVentaController {

    private final IDetalleVentaService detalleVentaService;

    public DetalleVentaController(IDetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    @GetMapping
    public ResponseEntity<List<DetalleVenta>> listar() {
        List<DetalleVenta> detalles = detalleVentaService.listarDetalles();
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/{codigoDetalleVenta}")
    public ResponseEntity<DetalleVenta> buscarPorCodigo(@PathVariable Long codigoDetalleVenta) {
        return detalleVentaService.buscarPorCodigoDetalleVenta(codigoDetalleVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody DetalleVenta detalleVenta) {
        try {
            DetalleVenta nuevoDetalle = detalleVentaService.guardar(detalleVenta);
            return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoDetalleVenta}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigoDetalleVenta) {
        try {
            if (!detalleVentaService.existePorCodigoDetalleVenta(codigoDetalleVenta)) {
                return ResponseEntity.notFound().build();
            }
            detalleVentaService.eliminar(codigoDetalleVenta);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigoDetalleVenta}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigoDetalleVenta, @RequestBody DetalleVenta detalleVenta) {
        try {
            if (!detalleVentaService.existePorCodigoDetalleVenta(codigoDetalleVenta)) {
                return ResponseEntity.notFound().build();
            }
            DetalleVenta detalleActualizado = detalleVentaService.actualizar(codigoDetalleVenta, detalleVenta);
            return ResponseEntity.ok(detalleActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}