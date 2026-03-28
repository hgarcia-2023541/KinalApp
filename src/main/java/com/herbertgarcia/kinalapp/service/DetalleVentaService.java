package com.herbertgarcia.kinalapp.service;

import com.herbertgarcia.kinalapp.entity.DetalleVenta;
import com.herbertgarcia.kinalapp.entity.Venta;
import com.herbertgarcia.kinalapp.repository.DetalleVentaRepository;
import com.herbertgarcia.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleVentaService implements IDetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
    private final VentaRepository ventaRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository, VentaRepository ventaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.ventaRepository = ventaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarTodos() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        validarDetalleVenta(detalleVenta);
        calcularSubtotal(detalleVenta);
        DetalleVenta guardado = detalleVentaRepository.save(detalleVenta);
        recalcularTotalVenta(detalleVenta.getVenta());
        return guardado;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleVenta> buscarPorId(Long id) {
        return detalleVentaRepository.findById(id);
    }

    @Override
    public DetalleVenta actualizar(Long id, DetalleVenta detalleVenta) {
        if (!detalleVentaRepository.existsById(id)) {
            throw new RuntimeException("DetalleVenta no encontrado con id: " + id);
        }
        detalleVenta.setCodigoDetalleVenta(id);
        validarDetalleVenta(detalleVenta);
        calcularSubtotal(detalleVenta);
        DetalleVenta actualizado = detalleVentaRepository.save(detalleVenta);
        recalcularTotalVenta(detalleVenta.getVenta());
        return actualizado;
    }

    @Override
    public void eliminar(Long id) {
        DetalleVenta detalle = detalleVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DetalleVenta no encontrado con id: " + id));
        Venta venta = detalle.getVenta();
        detalleVentaRepository.deleteById(id);
        recalcularTotalVenta(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return detalleVentaRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarPorVenta(Long codigoVenta) {
        return detalleVentaRepository.findByVenta_CodigoVenta(codigoVenta);
    }

    private void calcularSubtotal(DetalleVenta detalleVenta) {
        BigDecimal cantidad = BigDecimal.valueOf(detalleVenta.getCantidad());
        BigDecimal subtotal = detalleVenta.getPrecioUnitario().multiply(cantidad);
        detalleVenta.setSubtotal(subtotal);
    }

    private void recalcularTotalVenta(Venta venta) {
        if (venta == null) return;
        BigDecimal total = detalleVentaRepository
                .findByVenta_CodigoVenta(venta.getCodigoVenta())
                .stream()
                .map(d -> d.getSubtotal() != null ? d.getSubtotal() : new BigDecimal("0.00"))
                .reduce(new BigDecimal("0.00"), BigDecimal::add);
        venta.setTotal(total);
        ventaRepository.save(venta);
    }

    private void validarDetalleVenta(DetalleVenta detalleVenta) {
        if (detalleVenta.getCantidad() == null || detalleVenta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad es obligatoria y debe ser mayor a 0");
        }
        if (detalleVenta.getPrecioUnitario() == null) {
            throw new IllegalArgumentException("El precio unitario es obligatorio");
        }
        if (detalleVenta.getVenta() == null) {
            throw new IllegalArgumentException("La venta es obligatoria");
        }
        if (detalleVenta.getProducto() == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }
    }
}