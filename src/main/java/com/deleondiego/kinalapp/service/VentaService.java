package com.deleondiego.kinalapp.service;

import com.deleondiego.kinalapp.entity.Venta;
import com.deleondiego.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta venta) {
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigoVenta(Long codigoVenta) {
        return ventaRepository.findById(codigoVenta);
    }

    @Override
    public Venta actualizar(Long codigoVenta, Venta ventaNueva) {
        if (!ventaRepository.existsById(codigoVenta)) {
            throw new RuntimeException("No se encontró la venta con el código: " + codigoVenta);
        }

        validarVenta(ventaNueva);

        ventaNueva.setCodigoVenta(codigoVenta);

        return ventaRepository.save(ventaNueva);
    }

    @Override
    public void eliminar(Long codigoVenta) {
        if (!ventaRepository.existsById(codigoVenta)) {
            throw new RuntimeException("No se puede eliminar si el codigo no existe: " + codigoVenta);
        }
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigoVenta(Long codigoVenta) {
        return ventaRepository.existsById(codigoVenta);
    }

    private void validarVenta(Venta venta) {
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("La venta debe tener un cliente");
        }
        if (venta.getUsuario() == null) {
            throw new IllegalArgumentException("La venta debe tener un Usuario");
        }
        if (venta.getTotal() == null || venta.getTotal().doubleValue() < 0) {
            throw new IllegalArgumentException("El total de la venta no puede ser 0 o negativa");
        }
        if (venta.getFechaVenta() == null) {
            throw new IllegalArgumentException("La fecha de venta es obligatoria");
        }
    }
}