package blackdrive.cl.financiamiento_service.service;

import blackdrive.cl.financiamiento_service.clients.ClientesFeign;
import blackdrive.cl.financiamiento_service.clients.VehiculosFeign;
import blackdrive.cl.financiamiento_service.dto.ClientesDto;
import blackdrive.cl.financiamiento_service.dto.FinanciamientoDto;
import blackdrive.cl.financiamiento_service.dto.VehiculosDto;
import blackdrive.cl.financiamiento_service.mapper.FinanciamientoMapper;
import blackdrive.cl.financiamiento_service.model.FinanciamientoModel;
import blackdrive.cl.financiamiento_service.repository.FinanciamientoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanciamientoService {

    private static final Logger log = LoggerFactory.getLogger(FinanciamientoService.class);

    @Autowired
    private FinanciamientoRepository financiamientoRepository;
    @Autowired
    private FinanciamientoMapper financiamientoMapper;
    @Autowired
    private ClientesFeign clientesFeign;
    @Autowired
    private VehiculosFeign vehiculosFeign;

    private ClientesDto buscarCliente(Long id) {
        try {
            return clientesFeign.buscarPorId(id);
        } catch (Exception e) {
            log.warn("No se pudo obtener cliente con id {}: {}", id, e.getMessage());
            return null;
        }
    }

    private VehiculosDto buscarVehiculo(Long id) {
        try {
            return vehiculosFeign.buscarPorId(id);
        } catch (Exception e) {
            log.warn("No se pudo obtener vehículo con id {}: {}", id, e.getMessage());
            return null;
        }
    }

    public List<FinanciamientoDto> findAll() {
        log.info("Consultando todos los financiamientos");
        return financiamientoMapper.toListDTO(
                financiamientoRepository.findAll(),
                id -> buscarCliente(id),
                id -> buscarVehiculo(id)
        );
    }

    public FinanciamientoDto findById(Long id) {
        log.info("Buscando financiamiento con id: {}", id);
        FinanciamientoModel financiamiento = financiamientoRepository.findById(id).orElse(null);
        if (financiamiento == null) {
            log.warn("Financiamiento con id {} no encontrado", id);
            return null;
        }
        ClientesDto cliente = buscarCliente(financiamiento.getClienteId());
        VehiculosDto vehiculo = buscarVehiculo(financiamiento.getVehiculoId());
        return financiamientoMapper.toDTO(financiamiento, cliente, vehiculo);
    }

    public FinanciamientoModel save(FinanciamientoModel financiamiento) {
        if (financiamiento.getMontoTotal() != null && financiamiento.getMontoTotal() < 0) {
            log.error("Monto total negativo: {}", financiamiento.getMontoTotal());
            throw new IllegalArgumentException("El monto total no puede ser negativo");
        }
        log.info("Guardando financiamiento de {} cuotas", financiamiento.getCuotas());
        return financiamientoRepository.save(financiamiento);
    }

    public void delete(Long id) {
        log.info("Eliminando financiamiento con id: {}", id);
        financiamientoRepository.deleteById(id);
    }

    public List<FinanciamientoModel> findByEstado(String estado) {
        log.info("Buscando financiamientos por estado: {}", estado);
        return financiamientoRepository.findByEstado(estado);
    }

    public List<FinanciamientoModel> findByCuotas(Integer cuotas) {
        log.info("Buscando financiamientos con {} cuotas", cuotas);
        return financiamientoRepository.findByCuotas(cuotas);
    }

    public List<FinanciamientoModel> findByCliente(Long clienteId) {
        log.info("Buscando financiamientos del cliente: {}", clienteId);
        return financiamientoRepository.findByClienteId(clienteId);
    }
}