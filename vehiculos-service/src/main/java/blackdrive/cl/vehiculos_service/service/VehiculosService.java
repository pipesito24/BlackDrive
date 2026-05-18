package blackdrive.cl.vehiculos_service.service;

import blackdrive.cl.vehiculos_service.clients.ClientesFeign;
import blackdrive.cl.vehiculos_service.dto.ClientesDto;
import blackdrive.cl.vehiculos_service.dto.VehiculosDto;
import blackdrive.cl.vehiculos_service.mapper.VehiculosMapper;
import blackdrive.cl.vehiculos_service.model.VehiculosModel;
import blackdrive.cl.vehiculos_service.repository.VehiculosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculosService {

    private static final Logger log = LoggerFactory.getLogger(VehiculosService.class);

    @Autowired
    private VehiculosRepository vehiculosRepository;
    @Autowired
    private VehiculosMapper vehiculosMapper;
    @Autowired
    private ClientesFeign clienteFeign;

    private ClientesDto buscarCliente(Long id) {
        if (id == null) return null;
        try {
            return clienteFeign.buscarPorId(id);
        } catch (Exception e) {
            log.warn("No se pudo obtener cliente con id {}: {}", id, e.getMessage());
            return null;
        }
    }

    public List<VehiculosDto> findAll() {
        log.info("Consultando todos los vehículos");
        return vehiculosMapper.toListDTO(vehiculosRepository.findAll(), id -> buscarCliente(id));
    }

    public VehiculosDto findById(Long id) {
        log.info("Buscando vehículo con id: {}", id);
        VehiculosModel vehiculo = vehiculosRepository.findById(id).orElse(null);
        if (vehiculo == null) {
            log.warn("Vehículo con id {} no encontrado", id);
            return null;
        }
        ClientesDto cliente = buscarCliente(vehiculo.getClienteId());
        return vehiculosMapper.toDTO(vehiculo, cliente);
    }

    public VehiculosModel save(VehiculosModel vehiculo) {
        if (vehiculo.getPrecio() != null && vehiculo.getPrecio() < 0) {
            log.error("Precio negativo: {}", vehiculo.getPrecio());
            throw new IllegalArgumentException("El precio del vehículo no puede ser negativo");
        }
        if (vehiculo.getAno() != null && vehiculo.getAno() < 1900) {
            log.error("Año inválido: {}", vehiculo.getAno());
            throw new IllegalArgumentException("El año del vehículo no es válido");
        }
        if (vehiculo.getAno() != null && vehiculo.getAno() > 2026) {
            log.error("Año mayor al actual: {}", vehiculo.getAno());
            throw new IllegalArgumentException("El año del vehículo no puede ser mayor al año actual");
        }
        log.info("Guardando vehículo: {} {}", vehiculo.getMarca(), vehiculo.getModelo());
        return vehiculosRepository.save(vehiculo);
    }

    public void delete(Long id) {
        log.info("Eliminando vehículo con id: {}", id);
        vehiculosRepository.deleteById(id);
    }

    public List<VehiculosModel> findByMarca(String marca) {
        log.info("Buscando vehículos por marca: {}", marca);
        return vehiculosRepository.findByMarca(marca);
    }

    public List<VehiculosModel> findByAno(Integer ano) {
        log.info("Buscando vehículos por año: {}", ano);
        return vehiculosRepository.findByAno(ano);
    }

    public List<VehiculosModel> findByPrecio(Integer min, Integer max) {
        log.info("Buscando vehículos con precio entre {} y {}", min, max);
        return vehiculosRepository.findByPrecioBetween(min, max);
    }
}
