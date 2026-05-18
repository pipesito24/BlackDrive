package blackdrive.cl.facturas_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class FacturasDto {
    private Long id;
    private String numeroFactura;
    private Integer montoTotal;
    private Integer iva;
    private Integer montoNeto;
    private LocalDate fechaEmision;
    private String estado;
    private ClientesDto cliente;
    private PagosDto pago;
}