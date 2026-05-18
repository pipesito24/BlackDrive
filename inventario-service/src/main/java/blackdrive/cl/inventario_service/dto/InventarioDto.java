package blackdrive.cl.inventario_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventarioDto {
    private Long id;
    private String nombreProducto;
    private String categoria;
    private Integer stock;
    private Integer precioUnitario;
    private VehiculosDto vehiculo;
}