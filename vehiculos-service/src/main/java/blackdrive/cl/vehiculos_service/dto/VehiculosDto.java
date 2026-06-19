package blackdrive.cl.vehiculos_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehiculosDto {
    private Long id;
    private String marca;
    private String modelo;
    private int ano;
    private Integer precio;
    private ClientesDto cliente;
    private String estadoPropiedad;
}
