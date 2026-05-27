package blackdrive.cl.pagos_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagos")
public class PagosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El monto del pago es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Integer monto;

    @NotNull(message = "La fecha del pago es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    private Long clienteId;
}
