package blackdrive.cl.servicios_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ServiciosDto {
    private Long id;
    private String tipoServicio;
    private String descripcion;
    private Integer costo;
    private LocalDate fechaServicio;
    private String estado;
    private VehiculosDto vehiculo;
}