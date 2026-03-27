package com.deleondiego.kinalapp.service;

import com.deleondiego.kinalapp.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<Producto> listarProductos();
    Producto guardar(Producto producto);
    Optional<Producto> buscarPorCodigo(int codigoProducto);
    Producto actualizar(int codigoProducto, Producto producto);
    void eliminar(int codigoProducto);
    boolean existePorCodigo(int codigoProducto);
}
