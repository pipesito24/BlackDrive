package blackdrive.cl.clientes_service.mapper;

import blackdrive.cl.clientes_service.dto.ClientesDto;
import blackdrive.cl.clientes_service.model.ClientesModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientesMapper {

    public ClientesDto toDTO(ClientesModel cliente) {
        if (cliente == null) return null;
        ClientesDto dto = new ClientesDto();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getCorreo());
        dto.setSueldo(cliente.getSueldo());
        return dto;
    }

    public List<ClientesDto> toListDTO(List<ClientesModel> clientes) {
        return clientes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
