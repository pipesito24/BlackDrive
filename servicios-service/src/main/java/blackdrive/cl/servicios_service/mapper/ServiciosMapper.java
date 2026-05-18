package blackdrive.cl.servicios_service.mapper;

import blackdrive.cl.servicios_service.dto.ServiciosDto;
import blackdrive.cl.servicios_service.dto.VehiculosDto;
import blackdrive.cl.servicios_service.model.ServiciosModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ServiciosMapper {

    public ServiciosDto toDTO(ServiciosModel servicio, VehiculosDto vehiculo) {
        if (servicio == null) return null;
        ServiciosDto dto = new ServiciosDto();
        dto.setId(servicio.getId());
        dto.setTipoServicio(servicio.getTipoServicio());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setCosto(servicio.getCosto());
        dto.setFechaServicio(servicio.getFechaServicio());
        dto.setEstado(servicio.getEstado());
        dto.setVehiculo(vehiculo);
        return dto;
    }

    public List<ServiciosDto> toListDTO(List<ServiciosModel> servicios,
                                        Function<Long, VehiculosDto> vehiculoFetcher) {
        return servicios.stream()
                .map(s -> toDTO(s, vehiculoFetcher.apply(s.getVehiculoId())))
                .collect(Collectors.toList());
    }
}