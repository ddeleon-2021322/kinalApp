package com.deleondiego.kinalapp.service;

import com.deleondiego.kinalapp.entity.Venta;

import java.util.List;
import java.util.Optional;

public interface IVentaService {
    List<Venta> listarVentas();
    Venta guardar(Venta venta);
    Optional<Venta> buscarPorCodigoVenta(Long codigoVenta);
    Venta actualizar (Long codigoVente, Venta venta);
    void eliminar (Long codigoVenta);
    boolean existePorCodigoVenta(Long codigoVenta);

}
