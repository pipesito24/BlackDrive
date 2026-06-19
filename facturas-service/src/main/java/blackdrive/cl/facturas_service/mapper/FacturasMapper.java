package blackdrive.cl.facturas_service.mapper;

import blackdrive.cl.facturas_service.dto.ClientesDto;
import blackdrive.cl.facturas_service.dto.FacturasDto;
import blackdrive.cl.facturas_service.dto.PagosDto;
import blackdrive.cl.facturas_service.model.FacturasModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FacturasMapper {

    public FacturasDto toDTO(FacturasModel factura, ClientesDto cliente, PagosDto pago) {
        if (factura == null) return null;
        FacturasDto dto = new FacturasDto();
        dto.setId(factura.getId());
        dto.setNumeroFactura(factura.getNumeroFactura());
        dto.setMontoTotal(factura.getMontoTotal());
        dto.setIva(factura.getIva());
        dto.setMontoNeto(factura.getMontoNeto());
        dto.setFechaEmision(factura.getFechaEmision());
        dto.setEstado(factura.getEstado());
        dto.setCliente(cliente);
        dto.setPago(pago);
        return dto;
    }

    public List<FacturasDto> toListDTO(List<FacturasModel> facturas,
                                       Function<Long, ClientesDto> clienteFetcher,
                                       Function<Long, PagosDto> pagoFetcher) {
        return facturas.stream()
                .map(f -> toDTO(f, clienteFetcher.apply(f.getClienteId()), pagoFetcher.apply(f.getPagoId())))
                .collect(Collectors.toList());
    }
}
