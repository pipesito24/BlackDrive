package blackdrive.cl.pagos_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientesDto {
    private Long id;
    private String nombre;
    private String email;
    private Integer sueldo;
}
