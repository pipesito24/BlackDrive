package blackdrive.cl.financiamiento_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "financamiento")
public class FinanciamientoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cuotas;
    private Integer montoTotal;
    private Integer montoCuota;
    private LocalDate fechaInicio;
    private String estado;
    private Long clienteId;
    private Long vehiculoId;
}
