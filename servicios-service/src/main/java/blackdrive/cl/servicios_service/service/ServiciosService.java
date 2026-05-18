package blackdrive.cl.servicios_service.service;

import blackdrive.cl.servicios_service.clients.VehiculosFeign;
import blackdrive.cl.servicios_service.dto.ServiciosDto;
import blackdrive.cl.servicios_service.dto.VehiculosDto;
import blackdrive.cl.servicios_service.mapper.ServiciosMapper;
import blackdrive.cl.servicios_service.model.ServiciosModel;
import blackdrive.cl.servicios_service.repository.ServiciosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosService {

    private static final Logger log = LoggerFactory.getLogger(ServiciosService.class);

    @Autowired
    private ServiciosRepository serviciosRepository;
    @Autowired
    private ServiciosMapper serviciosMapper;
    @Autowired
    private VehiculosFeign vehiculoFeign;

    private VehiculosDto buscarVehiculo(Long id) {
        try {
            return vehiculoFeign.buscarPorId(id);
        } catch (Exception e) {
            log.warn("No se pudo obtener vehículo con id {}: {}", id, e.getMessage());
            return null;
        }
    }

    public List<ServiciosDto> findAll() {
        log.info("Consultando todos los servicios técnicos");
        return serviciosMapper.toListDTO(serviciosRepository.findAll(), id -> buscarVehiculo(id));
    }

    public ServiciosDto findById(Long id) {
        log.info("Buscando servicio con id: {}", id);
        ServiciosModel servicio = serviciosRepository.findById(id).orElse(null);
        if (servicio == null) {
            log.warn("Servicio con id {} no encontrado", id);
            return null;
        }
        VehiculosDto vehiculo = buscarVehiculo(servicio.getVehiculoId());
        return serviciosMapper.toDTO(servicio, vehiculo);
    }

    public ServiciosModel save(ServiciosModel servicio) {
        if (servicio.getCosto() != null && servicio.getCosto() < 0) {
            log.error("Costo negativo: {}", servicio.getCosto());
            throw new IllegalArgumentException("El costo del servicio no puede ser negativo");
        }
        log.info("Guardando servicio: {}", servicio.getTipoServicio());
        return serviciosRepository.save(servicio);
    }

    public void delete(Long id) {
        log.info("Eliminando servicio con id: {}", id);
        serviciosRepository.deleteById(id);
    }

    public List<ServiciosModel> findByTipo(String tipoServicio) {
        log.info("Buscando servicios por tipo: {}", tipoServicio);
        return serviciosRepository.findByTipoServicio(tipoServicio);
    }

    public List<ServiciosModel> findByEstado(String estado) {
        log.info("Buscando servicios por estado: {}", estado);
        return serviciosRepository.findByEstado(estado);
    }

    public List<ServiciosModel> findByCosto(Integer min, Integer max) {
        log.info("Buscando servicios con costo entre {} y {}", min, max);
        return serviciosRepository.findByCostoBetween(min, max);
    }
}
