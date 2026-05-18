package blackdrive.cl.sucursales_service.mapper;


import blackdrive.cl.sucursales_service.dto.SucursalesDto;
import blackdrive.cl.sucursales_service.model.SucursalesModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SucursalesMapper {

    public SucursalesDto toDTO(SucursalesModel sucursal) {
        if (sucursal == null) return null;
        SucursalesDto dto = new SucursalesDto();
        dto.setId(sucursal.getId());
        dto.setNombre(sucursal.getNombre());
        dto.setCiudad(sucursal.getCiudad());
        dto.setDireccion(sucursal.getDireccion());
        return dto;
    }

    public List<SucursalesDto> toListDTO(List<SucursalesModel> sucursales) {
        return sucursales.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
