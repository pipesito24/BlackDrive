package blackdrive.cl.clientes_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientesDto {

    private Long id;
    private String nombre;
    private String email;
    private Integer sueldo;
}
