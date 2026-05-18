package blackdrive.cl.facturas_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "facturas")
public class FacturasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroFactura;
    private Integer montoTotal;
    private Integer iva;
    private Integer montoNeto;
    private LocalDate fechaEmision;
    private String estado;
    private Long clienteId;
    private Long pagoId;
}