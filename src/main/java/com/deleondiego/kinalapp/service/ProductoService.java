package com.deleondiego.kinalapp.service;

import com.deleondiego.kinalapp.entity.Cliente;
import com.deleondiego.kinalapp.entity.Producto;
import com.deleondiego.kinalapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService implements IProductoService{
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductos(){
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto){
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    @Override
    public Optional buscarPorCodigo(int codigoProducto){
        return productoRepository.findById(codigoProducto);
    }

    @Override
    public Producto actualizar(int codigoViejo, Producto productoNuevo){
        if (!productoRepository.existsById(codigoViejo)){
            throw new RuntimeException("No existe producto con ese codigo" + codigoViejo);
        }

        validarProducto(productoNuevo);
        if (codigoViejo != productoNuevo.getCodigoProducto()) {
            productoRepository.deleteById(codigoViejo);
        }
        return productoRepository.save(productoNuevo);
    }

    @Override
    public void eliminar(int codigoProducto) {
        if (!productoRepository.existsById(codigoProducto)){
            throw new RuntimeException("No hay producto con ese codigo" + codigoProducto);
        }
        productoRepository.deleteById(codigoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(int codigoProducto){
        return productoRepository.existsById(codigoProducto);
    }


    private void validarProducto(Producto producto){
        if (producto.getCodigoProducto() <= 0) {
            throw new IllegalArgumentException("El código del producto es un dato obligatorio");
        }
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre es un dato obligatorio");
        }
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <=0){
            throw new IllegalArgumentException("El preciop es un dato obligatorio y debe ser mayor a cero");
        }

    }
}
