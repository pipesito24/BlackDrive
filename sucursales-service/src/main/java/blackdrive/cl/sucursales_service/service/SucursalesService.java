package blackdrive.cl.sucursales_service.service;

import blackdrive.cl.sucursales_service.dto.SucursalesDto;
import blackdrive.cl.sucursales_service.mapper.SucursalesMapper;
import blackdrive.cl.sucursales_service.model.SucursalesModel;
import blackdrive.cl.sucursales_service.repository.SucursalesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalesService {

    private static final Logger log = LoggerFactory.getLogger(SucursalesService.class);

    @Autowired
    private SucursalesRepository sucursalesRepository;

    @Autowired
    private SucursalesMapper sucursalesMapper;

    public List<SucursalesDto> findAll() {
        log.info("Consultando todas las sucursales");
        return sucursalesMapper.toListDTO(sucursalesRepository.findAll());
    }

    public SucursalesDto findById(Long id) {
        log.info("Buscando sucursal con id: {}", id);
        SucursalesModel sucursal = sucursalesRepository.findById(id).orElse(null);
        if (sucursal == null) log.warn("Sucursal con id {} no encontrada", id);
        return sucursalesMapper.toDTO(sucursal);
    }

    public SucursalesModel save(SucursalesModel sucursal) {
        if (sucursal.getNombre() == null || sucursal.getNombre().isBlank()) {
            log.error("Intento de guardar sucursal sin nombre");
            throw new IllegalArgumentException("El nombre de la sucursal no puede estar vacío");
        }
        log.info("Guardando nueva sucursal: {}", sucursal.getNombre());
        return sucursalesRepository.save(sucursal);
    }

    public void delete(Long id) {
        log.info("Eliminando sucursal con id: {}", id);
        sucursalesRepository.deleteById(id);
    }

    public List<SucursalesModel> findByCiudad(String ciudad) {
        log.info("Buscando sucursales en ciudad: {}", ciudad);
        return sucursalesRepository.findByCiudad(ciudad);
    }

    public List<SucursalesModel> findByNombre(String nombre) {
        log.info("Buscando sucursales con nombre: {}", nombre);
        return sucursalesRepository.findByNombreContaining(nombre);
    }
}
