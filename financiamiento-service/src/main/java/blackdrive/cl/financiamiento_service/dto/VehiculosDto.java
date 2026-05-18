package blackdrive.cl.financiamiento_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehiculosDto {
    private Long id;
    private String marca;
    private String modelo;
    private Integer ano;
    private Integer precio;
}
