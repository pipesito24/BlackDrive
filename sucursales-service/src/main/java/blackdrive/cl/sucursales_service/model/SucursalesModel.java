package blackdrive.cl.sucursales_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sucursales")
public class SucursalesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección de la sucursal es obligatoria")
    private String direccion;

    @NotBlank(message = "La ciudad de la sucursal es obligatoria")
    private String ciudad;

    @NotBlank(message = "El teléfono de la sucursal es obligatorio")
    private String telefono;
}
