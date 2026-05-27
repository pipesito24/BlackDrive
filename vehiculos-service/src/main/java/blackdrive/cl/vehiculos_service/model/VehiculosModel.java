package blackdrive.cl.vehiculos_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "vehiculos")
public class VehiculosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "La marca del vehículo es obligatoria")
    private String marca;
    @NotBlank(message = "El modelo del vehículo es obligatorio")
    private String modelo;
    private Integer ano;
    @Positive(message = "El precio debe ser positivo")
    private Integer precio;
    private Long clienteId;
}

