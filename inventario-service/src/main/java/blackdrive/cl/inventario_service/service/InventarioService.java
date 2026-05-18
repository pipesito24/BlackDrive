package blackdrive.cl.inventario_service.service;

import blackdrive.cl.inventario_service.clients.VehiculosFeign;
import blackdrive.cl.inventario_service.dto.InventarioDto;
import blackdrive.cl.inventario_service.dto.VehiculosDto;
import blackdrive.cl.inventario_service.mapper.InventarioMapper;
import blackdrive.cl.inventario_service.model.InventarioModel;
import blackdrive.cl.inventario_service.repository.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);

    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private InventarioMapper inventarioMapper;
    @Autowired
    private VehiculosFeign vehiculosFeign;

    private VehiculosDto buscarVehiculo(Long id) {
        try {
            return vehiculosFeign.buscarPorId(id);
        } catch (Exception e) {
            log.warn("No se pudo obtener vehículo con id {}: {}", id, e.getMessage());
            return null;
        }
    }

    public List<InventarioDto> findAll() {
        log.info("Consultando todo el inventario");
        return inventarioMapper.toListDTO(inventarioRepository.findAll(), id -> buscarVehiculo(id));
    }

    public InventarioDto findById(Long id) {
        log.info("Buscando ítem de inventario con id: {}", id);
        InventarioModel inventario = inventarioRepository.findById(id).orElse(null);
        if (inventario == null) {
            log.warn("Ítem de inventario con id {} no encontrado", id);
            return null;
        }
        VehiculosDto vehiculo = buscarVehiculo(inventario.getVehiculoId());
        return inventarioMapper.toDTO(inventario, vehiculo);
    }

    public InventarioModel save(InventarioModel inventario) {
        if (inventario.getStock() != null && inventario.getStock() < 0) {
            log.error("Stock negativo: {}", inventario.getStock());
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (inventario.getPrecioUnitario() != null && inventario.getPrecioUnitario() < 0) {
            log.error("Precio unitario negativo: {}", inventario.getPrecioUnitario());
            throw new IllegalArgumentException("El precio unitario no puede ser negativo");
        }
        log.info("Guardando producto en inventario: {}", inventario.getNombreProducto());
        return inventarioRepository.save(inventario);
    }

    public void delete(Long id) {
        log.info("Eliminando ítem de inventario con id: {}", id);
        inventarioRepository.deleteById(id);
    }

    public List<InventarioModel> findByCategoria(String categoria) {
        log.info("Buscando inventario por categoría: {}", categoria);
        return inventarioRepository.findByCategoria(categoria);
    }

    public List<InventarioModel> findByStockBajo(Integer stock) {
        log.info("Buscando productos con stock menor a: {}", stock);
        return inventarioRepository.findByStockLessThan(stock);
    }

    public List<InventarioModel> findByPrecio(Integer min, Integer max) {
        log.info("Buscando inventario con precio entre {} y {}", min, max);
        return inventarioRepository.findByPrecioUnitarioBetween(min, max);
    }
}