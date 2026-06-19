package blackdrive.cl.vehiculos_service.mapper;

import blackdrive.cl.vehiculos_service.dto.ClientesDto;
import blackdrive.cl.vehiculos_service.dto.VehiculosDto;
import blackdrive.cl.vehiculos_service.model.VehiculosModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehiculosMapper {

    public VehiculosDto toDTO(VehiculosModel vehiculo, ClientesDto cliente) {
        if (vehiculo == null) return null;
        VehiculosDto dto = new VehiculosDto();
        dto.setId(vehiculo.getId());
        dto.setMarca(vehiculo.getMarca());
        dto.setModelo(vehiculo.getModelo());
        dto.setAno(vehiculo.getAno());
        dto.setPrecio(vehiculo.getPrecio());
        dto.setCliente(cliente);
        dto.setEstadoPropiedad(cliente != null ? "Con dueño" : "Disponible en stock");
        return dto;
    }

    public List<VehiculosDto> toListDTO(List<VehiculosModel> vehiculos,
                                        java.util.function.Function<Long, ClientesDto> clienteFetcher) {
        return vehiculos.stream()
                .map(v -> toDTO(v, clienteFetcher.apply(v.getClienteId())))
                .collect(Collectors.toList());
    }
}
