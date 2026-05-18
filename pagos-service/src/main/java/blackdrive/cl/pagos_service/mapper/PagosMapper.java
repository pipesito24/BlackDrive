package blackdrive.cl.pagos_service.mapper;

import blackdrive.cl.pagos_service.dto.ClientesDto;
import blackdrive.cl.pagos_service.dto.PagosDto;
import blackdrive.cl.pagos_service.model.PagosModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PagosMapper {

    public PagosDto toDTO(PagosModel pago, ClientesDto cliente) {
        if (pago == null) return null;
        PagosDto dto = new PagosDto();
        dto.setId(pago.getId());
        dto.setMonto(pago.getMonto());
        dto.setFecha(pago.getFecha());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setCliente(cliente);
        return dto;
    }

    public List<PagosDto> toListDTO(List<PagosModel> pagos,
                                    Function<Long, ClientesDto> clienteFetcher) {
        return pagos.stream()
                .map(p -> toDTO(p, clienteFetcher.apply(p.getClienteId())))
                .collect(Collectors.toList());
    }
}
