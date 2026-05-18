package blackdrive.cl.vendedores_service.service;

import blackdrive.cl.vendedores_service.clients.SucursalFeign;
import blackdrive.cl.vendedores_service.dto.SucursalesDto;
import blackdrive.cl.vendedores_service.dto.VendedoresDto;
import blackdrive.cl.vendedores_service.mapper.VendedoresMapper;
import blackdrive.cl.vendedores_service.model.VendedoresModel;
import blackdrive.cl.vendedores_service.repository.VendedoresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedoresService {

    private static final Logger log = LoggerFactory.getLogger(VendedoresService.class);

    @Autowired
    private VendedoresRepository vendedoresRepository;
    @Autowired
    private VendedoresMapper vendedoresMapper;
    @Autowired
    private SucursalFeign sucursalFeign;

    private SucursalesDto buscarSucursal(Long id) {
        try {
            return sucursalFeign.buscarPorId(id);
        } catch (Exception e) {
            log.warn("No se pudo obtener sucursal con id {}: {}", id, e.getMessage());
            return null;
        }
    }

    public List<VendedoresDto> findAll() {
        log.info("Consultando todos los vendedores");
        return vendedoresMapper.toListDTO(vendedoresRepository.findAll(), id -> buscarSucursal(id));
    }

    public VendedoresDto findById(Long id) {
        log.info("Buscando vendedor con id: {}", id);
        VendedoresModel vendedor = vendedoresRepository.findById(id).orElse(null);
        if (vendedor == null) {
            log.warn("Vendedor con id {} no encontrado", id);
            return null;
        }
        SucursalesDto sucursal = buscarSucursal(vendedor.getSucursalId());
        return vendedoresMapper.toDTO(vendedor, sucursal);
    }

    public VendedoresModel save(VendedoresModel vendedor) {
        if (vendedor.getEmail() == null || vendedor.getEmail().isBlank()) {
            log.error("Intento de guardar vendedor sin email");
            throw new IllegalArgumentException("El vendedor debe tener un email de contacto");
        }
        if (vendedor.getComision() != null && vendedor.getComision() < 0) {
            log.error("Comisión negativa: {}", vendedor.getComision());
            throw new IllegalArgumentException("La comisión no puede ser negativa");
        }
        log.info("Guardando vendedor: {}", vendedor.getNombre());
        return vendedoresRepository.save(vendedor);
    }

    public void delete(Long id) {
        log.info("Eliminando vendedor con id: {}", id);
        vendedoresRepository.deleteById(id);
    }

    public List<VendedoresModel> findBySucursal(Long sucursalId) {
        log.info("Buscando vendedores de sucursal: {}", sucursalId);
        return vendedoresRepository.findBySucursalId(sucursalId);
    }

    public List<VendedoresModel> findByComision(Double min, Double max) {
        log.info("Buscando vendedores con comisión entre {} y {}", min, max);
        return vendedoresRepository.findByComisionBetween(min, max);
    }
}