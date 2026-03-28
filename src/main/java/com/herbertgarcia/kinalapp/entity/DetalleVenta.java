package com.herbertgarcia.kinalapp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "DetalleVenta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_detalle_venta")
    private Long codigoDetalleVenta;

    @Column(name = "cantidad")
    private Long cantidad;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "Ventas_codigo_venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "Productos_codigo_producto")
    private Producto producto;

    public DetalleVenta() {}

    public DetalleVenta(Long codigoDetalleVenta, Long cantidad, BigDecimal precioUnitario, BigDecimal subtotal, Venta venta, Producto producto) {
        this.codigoDetalleVenta = codigoDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.venta = venta;
        this.producto = producto;
    }

    public Long getCodigoDetalleVenta() { return codigoDetalleVenta; }
    public void setCodigoDetalleVenta(Long codigoDetalleVenta) { this.codigoDetalleVenta = codigoDetalleVenta; }

    public Long getCantidad() { return cantidad; }
    public void setCantidad(Long cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
}