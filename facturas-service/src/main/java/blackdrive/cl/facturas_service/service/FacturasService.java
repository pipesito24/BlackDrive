package blackdrive.cl.facturas_service.service;

import blackdrive.cl.facturas_service.clients.ClientesFeign;
import blackdrive.cl.facturas_service.clients.PagosFeign;
import blackdrive.cl.facturas_service.dto.ClientesDto;
import blackdrive.cl.facturas_service.dto.FacturasDto;
import blackdrive.cl.facturas_service.dto.PagosDto;
import blackdrive.cl.facturas_service.mapper.FacturasMapper;
import blackdrive.cl.facturas_service.model.FacturasModel;
import blackdrive.cl.facturas_service.repository.FacturasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturasService {

    private static final Logger log = LoggerFactory.getLogger(FacturasService.class);

    @Autowired
    private FacturasRepository facturasRepository;
    @Autowired
    private FacturasMapper facturasMapper;
    @Autowired
    private ClientesFeign clientesFeign;
    @Autowired
    private PagosFeign pagosFeign;

    private ClientesDto buscarCliente(Long id) {
        try {
            return clientesFeign.buscarPorId(id);
        } catch (Exception e) {
            log.warn("No se pudo obtener cliente con id {}: {}", id, e.getMessage());
            return null;
        }
    }

    private PagosDto buscarPago(Long id) {
        try {
            return pagosFeign.buscarPorId(id);
        } catch (Exception e) {
            log.warn("No se pudo obtener pago con id {}: {}", id, e.getMessage());
            return null;
        }
    }

    public List<FacturasDto> findAll() {
        log.info("Consultando todas las facturas");
        return facturasMapper.toListDTO(
                facturasRepository.findAll(),
                id -> buscarCliente(id),
                id -> buscarPago(id)
        );
    }

    public FacturasDto findById(Long id) {
        log.info("Buscando factura con id: {}", id);
        FacturasModel factura = facturasRepository.findById(id).orElse(null);
        if (factura == null) {
            log.warn("Factura con id {} no encontrada", id);
            return null;
        }
        ClientesDto cliente = buscarCliente(factura.getClienteId());
        PagosDto pago = buscarPago(factura.getPagoId());
        return facturasMapper.toDTO(factura, cliente, pago);
    }

    public FacturasModel save(FacturasModel factura) {
        if (factura.getMontoTotal() != null && factura.getMontoTotal() <= 0) {
            log.error("Monto total inválido: {}", factura.getMontoTotal());
            throw new IllegalArgumentException("El monto total de la factura debe ser mayor a cero");
        }
        log.info("Guardando factura número: {}", factura.getNumeroFactura());
        return facturasRepository.save(factura);
    }

    public void delete(Long id) {
        log.info("Eliminando factura con id: {}", id);
        facturasRepository.deleteById(id);
    }

    public List<FacturasModel> findByEstado(String estado) {
        log.info("Buscando facturas por estado: {}", estado);
        return facturasRepository.findByEstado(estado);
    }

    public List<FacturasModel> findByCliente(Long clienteId) {
        log.info("Buscando facturas del cliente: {}", clienteId);
        return facturasRepository.findByClienteId(clienteId);
    }

    public List<FacturasModel> findByMonto(Integer min, Integer max) {
        log.info("Buscando facturas con monto entre {} y {}", min, max);
        return facturasRepository.findByMontoTotalBetween(min, max);
    }
}