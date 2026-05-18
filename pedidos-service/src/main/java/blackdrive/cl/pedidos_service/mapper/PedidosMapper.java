package blackdrive.cl.pedidos_service.mapper;

import blackdrive.cl.pedidos_service.dto.ClientesDto;
import blackdrive.cl.pedidos_service.dto.PedidosDto;
import blackdrive.cl.pedidos_service.model.PedidosModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PedidosMapper {

    public PedidosDto toDTO(PedidosModel pedido, ClientesDto cliente) {
        if (pedido == null) return null;
        PedidosDto dto = new PedidosDto();
        dto.setId(pedido.getId());
        dto.setDescripcion(pedido.getDescripcion());
        dto.setCantidad(pedido.getCantidad());
        dto.setPrecioUnitario(pedido.getPrecioUnitario());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());
        dto.setCliente(cliente);
        return dto;
    }

    public List<PedidosDto> toListDTO(List<PedidosModel> pedidos,
                                      Function<Long, ClientesDto> clienteFetcher) {
        return pedidos.stream()
                .map(p -> toDTO(p, clienteFetcher.apply(p.getClienteId())))
                .collect(Collectors.toList());
    }
}