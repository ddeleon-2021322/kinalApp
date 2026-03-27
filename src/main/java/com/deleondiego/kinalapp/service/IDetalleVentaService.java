package com.deleondiego.kinalapp.service;

import com.deleondiego.kinalapp.entity.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {
    List<DetalleVenta> listarDetalles();
    DetalleVenta guardar(DetalleVenta detalleVenta);
    Optional<DetalleVenta> buscarPorCodigoDetalleVenta(Long codigoDetalleVenta);
    DetalleVenta actualizar(Long codigoDetalleVenta, DetalleVenta detalleVenta);
    void eliminar(Long codigoDetalleVenta);
    boolean existePorCodigoDetalleVenta(Long codigoDetalleVenta);
}