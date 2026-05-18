package blackdrive.cl.facturas_service.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PagosDto {
    Long id;
    private int monto;
    private LocalDate fecha;
    private String metodoPago;
}
