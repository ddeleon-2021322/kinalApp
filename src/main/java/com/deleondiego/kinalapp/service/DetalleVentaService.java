package com.deleondiego.kinalapp.service;

import com.deleondiego.kinalapp.entity.DetalleVenta;
import com.deleondiego.kinalapp.repository.DetalleVentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleVentaService implements IDetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarDetalles() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        validarDetalleVenta(detalleVenta);
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleVenta> buscarPorCodigoDetalleVenta(Long codigoDetalleVenta) {
        return detalleVentaRepository.findById(codigoDetalleVenta);
    }

    @Override
    public DetalleVenta actualizar(Long codigoDetalleVenta, DetalleVenta detalleVentaNuevo) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
            throw new RuntimeException("No se encontró el detalle de la venta con ese codigo: " + codigoDetalleVenta);
        }

        validarDetalleVenta(detalleVentaNuevo);

        detalleVentaNuevo.setCodigoDetalleVenta(codigoDetalleVenta);

        return detalleVentaRepository.save(detalleVentaNuevo);
    }

    @Override
    public void eliminar(Long codigoDetalleVenta) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
            throw new RuntimeException("No se puede eliminar si el código no existe: " + codigoDetalleVenta);
        }
        detalleVentaRepository.deleteById(codigoDetalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigoDetalleVenta(Long codigoDetalleVenta) {
        return detalleVentaRepository.existsById(codigoDetalleVenta);
    }

    private void validarDetalleVenta(DetalleVenta detalleVenta) {
        if (detalleVenta.getProducto() == null) {
            throw new IllegalArgumentException("El detalle debe estar asociado a un Producto");
        }
        if (detalleVenta.getVenta() == null) {
            throw new IllegalArgumentException("El detalle debe pertenecer a una Venta");
        }
        if (detalleVenta.getCantidad() == null || detalleVenta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (detalleVenta.getPrecioUnitario() == null || detalleVenta.getPrecioUnitario().doubleValue() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (detalleVenta.getSubtotal() == null || detalleVenta.getSubtotal().doubleValue() < 0) {
            throw new IllegalArgumentException("El subtotal no puede ser negativo");
        }
    }
}