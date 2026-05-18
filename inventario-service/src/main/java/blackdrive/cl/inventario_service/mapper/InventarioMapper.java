package blackdrive.cl.inventario_service.mapper;

import blackdrive.cl.inventario_service.dto.InventarioDto;
import blackdrive.cl.inventario_service.dto.VehiculosDto;
import blackdrive.cl.inventario_service.model.InventarioModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class InventarioMapper {

    public InventarioDto toDTO(InventarioModel inventario, VehiculosDto vehiculo) {
        if (inventario == null) return null;
        InventarioDto dto = new InventarioDto();
        dto.setId(inventario.getId());
        dto.setNombreProducto(inventario.getNombreProducto());
        dto.setCategoria(inventario.getCategoria());
        dto.setStock(inventario.getStock());
        dto.setPrecioUnitario(inventario.getPrecioUnitario());
        dto.setVehiculo(vehiculo);
        return dto;
    }

    public List<InventarioDto> toListDTO(List<InventarioModel> inventarios,
                                         Function<Long, VehiculosDto> vehiculoFetcher) {
        return inventarios.stream()
                .map(i -> toDTO(i, vehiculoFetcher.apply(i.getVehiculoId())))
                .collect(Collectors.toList());
    }
}