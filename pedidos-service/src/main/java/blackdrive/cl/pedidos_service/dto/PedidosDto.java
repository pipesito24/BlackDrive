package blackdrive.cl.pedidos_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PedidosDto {
    private Long id;
    private String descripcion;
    private Integer cantidad;
    private Integer precioUnitario;
    private LocalDate fechaPedido;
    private String estado;
    private ClientesDto cliente;
}
