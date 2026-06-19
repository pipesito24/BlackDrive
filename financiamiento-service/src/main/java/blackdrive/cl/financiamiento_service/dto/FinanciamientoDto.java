package blackdrive.cl.financiamiento_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class FinanciamientoDto {
    private Long id;
    private Integer cuotas;
    private Integer montoTotal;
    private Integer montoCuota;
    private LocalDate fechaInicio;
    private String estado;
    private ClientesDto cliente;
    private VehiculosDto vehiculo;
}
