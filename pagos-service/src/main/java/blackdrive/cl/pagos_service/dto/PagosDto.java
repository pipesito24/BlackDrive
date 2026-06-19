package blackdrive.cl.pagos_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PagosDto {
    private Long id;
    private Integer monto;
    private LocalDate fecha;
    private String metodoPago;
    private ClientesDto cliente;
}
