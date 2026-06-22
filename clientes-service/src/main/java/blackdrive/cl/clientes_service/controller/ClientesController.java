package blackdrive.cl.clientes_service.controller;

import blackdrive.cl.clientes_service.dto.ClientesDto;
import blackdrive.cl.clientes_service.model.ClientesModel;
import blackdrive.cl.clientes_service.service.ClientesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Clientes",
        description = "Operaciones relacionadas con la gestión de clientes"
)
@RestController
@RequestMapping("/api/v6/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

    @Operation(
            summary = "Obtener todos los clientes",
            description = "Retorna el listado completo de clientes registrados en el sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "[{\"id\":1,\"nombre\":\"Juan Pérez\",\"correo\":\"juan@gmail.com\",\"sueldo\":500000}]"
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<ClientesDto>> findAll() {
        return ResponseEntity.ok(clientesService.findAll());
    }

    @Operation(
            summary = "Obtener cliente por ID",
            description = "Retorna los datos de un cliente según su identificador único"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "{\"id\":1,\"nombre\":\"Juan Pérez\",\"correo\":\"juan@gmail.com\",\"sueldo\":500000}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientesDto> findById(@PathVariable Long id) {
        ClientesDto dto = clientesService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Registrar un nuevo cliente",
            description = "Crea un nuevo cliente en el sistema. El sueldo debe ser positivo y el correo debe ser válido"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente registrado correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "\"Cliente agregado correctamente\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos: sueldo negativo, correo mal formado o campo obligatorio vacío",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"mensaje\":\"El sueldo ingresado no es válido: -1000\"}")
                    )
            )
    })
    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody ClientesModel cliente) {
        clientesService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente agregado correctamente");
    }

    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina un cliente existente según su identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente eliminado correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "\"Cliente eliminado correctamente\"")
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        clientesService.delete(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }

    @Operation(
            summary = "Buscar cliente por correo",
            description = "Retorna el cliente que coincide con el correo electrónico indicado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "{\"id\":1,\"nombre\":\"Juan Pérez\",\"correo\":\"juan@gmail.com\",\"direccion\":\"Av. Siempre Viva 123\",\"sueldo\":500000}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/correo/{correo}")
    public ResponseEntity<ClientesModel> findByCorreo(@PathVariable String correo) {
        ClientesModel cliente = clientesService.findByCorreo(correo);
        if (cliente == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cliente);
    }

    @Operation(
            summary = "Buscar clientes por rango de sueldo",
            description = "Retorna los clientes cuyo sueldo se encuentra entre un mínimo y un máximo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "[{\"id\":1,\"nombre\":\"Juan Pérez\",\"correo\":\"juan@gmail.com\",\"direccion\":\"Av. Siempre Viva 123\",\"sueldo\":500000}]"
                            )
                    )
            )
    })
    @GetMapping("/sueldo/{min}/{max}")
    public ResponseEntity<List<ClientesModel>> findBySueldo(@PathVariable Integer min, @PathVariable Integer max) {
        return ResponseEntity.ok(clientesService.findBySueldo(min, max));
    }

    @Operation(
            summary = "Buscar clientes por nombre",
            description = "Retorna los clientes cuyo nombre contiene el texto indicado (búsqueda parcial)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "[{\"id\":1,\"nombre\":\"Juan Pérez\",\"correo\":\"juan@gmail.com\",\"direccion\":\"Av. Siempre Viva 123\",\"sueldo\":500000}]"
                            )
                    )
            )
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ClientesModel>> findByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(clientesService.findByNombre(nombre));
    }
}