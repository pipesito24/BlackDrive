package blackdrive.cl.clientes_service.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
public class ClientesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombre;
    @Email(message = "El correo ingresado no es válido")
    private String correo;
    @NotBlank
    private String direccion;
    @Positive(message = "El sueldo debe ser positivo")
    private Integer sueldo;
}
