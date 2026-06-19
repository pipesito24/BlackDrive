package blackdrive.cl.vehiculos_service.service;

import blackdrive.cl.vehiculos_service.clients.ClientesFeign;
import blackdrive.cl.vehiculos_service.dto.ClientesDto;
import blackdrive.cl.vehiculos_service.dto.VehiculosDto;
import blackdrive.cl.vehiculos_service.exception.PrecioVehiculoInvalidoException;
import blackdrive.cl.vehiculos_service.exception.VehiculoNoEncontradoException;
import blackdrive.cl.vehiculos_service.mapper.VehiculosMapper;
import blackdrive.cl.vehiculos_service.model.VehiculosModel;
import blackdrive.cl.vehiculos_service.repository.VehiculosRepository;
import org.slf4j.Logger;
import blackdrive.cl.vehiculos_service.exception.AnoVehiculoInvalidoException;
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

        VehiculosModel vehiculo = vehiculosRepository.findById(id)
                .orElseThrow(() ->
                        new VehiculoNoEncontradoException(id));

        ClientesDto cliente = buscarCliente(vehiculo.getClienteId());

        return vehiculosMapper.toDTO(vehiculo, cliente);
    }

    public VehiculosModel save(VehiculosModel vehiculo) {
        //Creacion validacion personalizada
        if (vehiculo.getPrecio() < 1000000) {
            throw new PrecioVehiculoInvalidoException(
                    "El vehículo no cumple el precio mínimo permitido por BlackDrive"
            );
        }
        if (vehiculo.getAno() != null && vehiculo.getAno() < 1900) {
            log.error("Año inválido: {}", vehiculo.getAno());
            throw new AnoVehiculoInvalidoException(vehiculo.getAno());
        }
        if (vehiculo.getAno() != null && vehiculo.getAno() > 2026) {
            log.error("Año mayor al actual: {}", vehiculo.getAno());
            throw new AnoVehiculoInvalidoException(vehiculo.getAno());
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
