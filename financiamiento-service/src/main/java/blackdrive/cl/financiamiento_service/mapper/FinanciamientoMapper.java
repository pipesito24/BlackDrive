package blackdrive.cl.financiamiento_service.mapper;

import blackdrive.cl.financiamiento_service.dto.ClientesDto;
import blackdrive.cl.financiamiento_service.dto.FinanciamientoDto;
import blackdrive.cl.financiamiento_service.dto.VehiculosDto;
import blackdrive.cl.financiamiento_service.model.FinanciamientoModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FinanciamientoMapper {

    public FinanciamientoDto toDTO(FinanciamientoModel financiamiento,
                                   ClientesDto cliente,
                                   VehiculosDto vehiculo) {
        if (financiamiento == null) return null;
        FinanciamientoDto dto = new FinanciamientoDto();
        dto.setId(financiamiento.getId());
        dto.setCuotas(financiamiento.getCuotas());
        dto.setMontoTotal(financiamiento.getMontoTotal());
        dto.setMontoCuota(financiamiento.getMontoCuota());
        dto.setFechaInicio(financiamiento.getFechaInicio());
        dto.setEstado(financiamiento.getEstado());
        dto.setCliente(cliente);
        dto.setVehiculo(vehiculo);
        return dto;
    }

    public List<FinanciamientoDto> toListDTO(List<FinanciamientoModel> financiamientos,
                                             Function<Long, ClientesDto> clienteFetcher,
                                             Function<Long, VehiculosDto> vehiculoFetcher) {
        return financiamientos.stream()
                .map(f -> toDTO(f,
                        clienteFetcher.apply(f.getClienteId()),
                        vehiculoFetcher.apply(f.getVehiculoId())))
                .collect(Collectors.toList());
    }
}
