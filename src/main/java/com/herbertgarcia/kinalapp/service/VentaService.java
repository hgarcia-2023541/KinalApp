package com.herbertgarcia.kinalapp.service;

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
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    public VentaService(VentaRepository ventaRepository, DetalleVentaRepository detalleVentaRepository) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarTodos() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta venta) {
        validarVenta(venta);
        if (venta.getEstado() == null || venta.getEstado() == 0) {
            venta.setEstado(1L);
        }
        venta.setTotal(new BigDecimal("0.00"));
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorId(Long id) {
        return ventaRepository.findById(id);
    }

    @Override
    public Venta actualizar(Long id, Venta venta) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con id: " + id);
        }
        venta.setCodigoVenta(id);
        validarVenta(venta);
        BigDecimal total = detalleVentaRepository
                .findByVenta_CodigoVenta(id)
                .stream()
                .map(d -> d.getSubtotal() != null ? d.getSubtotal() : new BigDecimal("0.00"))
                .reduce(new BigDecimal("0.00"), BigDecimal::add);
        venta.setTotal(total);
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con id: " + id);
        }
        ventaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return ventaRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarActivos() {
        return ventaRepository.findByEstado(1L);
    }

    private void validarVenta(Venta venta) {
        if (venta.getFechaVenta() == null) {
            throw new IllegalArgumentException("La fecha de venta es obligatoria");
        }
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (venta.getUsuario() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
    }
}