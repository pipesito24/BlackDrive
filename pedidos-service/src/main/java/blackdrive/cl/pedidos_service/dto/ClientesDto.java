package blackdrive.cl.pedidos_service.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientesDto {
    private Long id;
    private String nombre;
    private String correo;
    private Integer sueldo;
}
