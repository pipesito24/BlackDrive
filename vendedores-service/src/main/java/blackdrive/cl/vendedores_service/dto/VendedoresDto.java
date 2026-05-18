package blackdrive.cl.vendedores_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VendedoresDto {
    private Long id;
    private String nombre;
    private String email;
    private Double comision;
    private SucursalesDto sucursal;
}
