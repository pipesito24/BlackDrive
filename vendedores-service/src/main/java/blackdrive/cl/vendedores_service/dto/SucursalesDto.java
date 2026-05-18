package blackdrive.cl.vendedores_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SucursalesDto {
    private Long id;
    private String nombre;
    private String ciudad;
}
