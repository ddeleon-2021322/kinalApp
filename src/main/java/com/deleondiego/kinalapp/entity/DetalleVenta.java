package com.deleondiego.kinalapp.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table (name = "DetalleVentas")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_detalle_venta")
    private Long codigoDetalleVenta;
    @Column
    private Long cantidad;
    @Column
    private BigDecimal precioUnitario = new BigDecimal(10.2);
    @Column
    private BigDecimal subtotal = new BigDecimal(10.2);

    @ManyToOne
    @JoinColumn(name = "Productos_codigo_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "Ventas_codigo_venta")
    private  Venta venta;

    public DetalleVenta() {
    }

    public DetalleVenta(Long codigoDetalleVenta, Long cantidad, BigDecimal precioUnitario, BigDecimal subtotal, Producto producto, Venta venta) {
        this.codigoDetalleVenta = codigoDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.producto = producto;
        this.venta = venta;
    }

    public Long getCodigoDetalleVenta() {
        return codigoDetalleVenta;
    }

    public void setCodigoDetalleVenta(Long codigoDetalleVenta) {
        this.codigoDetalleVenta = codigoDetalleVenta;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
