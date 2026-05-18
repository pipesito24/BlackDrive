package blackdrive.cl.vendedores_service.mapper;

import blackdrive.cl.vendedores_service.dto.SucursalesDto;
import blackdrive.cl.vendedores_service.dto.VendedoresDto;
import blackdrive.cl.vendedores_service.model.VendedoresModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class VendedoresMapper {

    public VendedoresDto toDTO(VendedoresModel vendedor, SucursalesDto sucursal) {
        if (vendedor == null) return null;
        VendedoresDto dto = new VendedoresDto();
        dto.setId(vendedor.getId());
        dto.setNombre(vendedor.getNombre());
        dto.setEmail(vendedor.getEmail());
        dto.setComision(vendedor.getComision());
        dto.setSucursal(sucursal);
        return dto;
    }

    public List<VendedoresDto> toListDTO(List<VendedoresModel> vendedores,
                                         Function<Long, SucursalesDto> sucursalFetcher) {
        return vendedores.stream()
                .map(v -> toDTO(v, sucursalFetcher.apply(v.getSucursalId())))
                .collect(Collectors.toList());
    }
}
