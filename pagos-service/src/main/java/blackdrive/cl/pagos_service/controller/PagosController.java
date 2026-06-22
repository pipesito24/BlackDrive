package blackdrive.cl.pagos_service.controller;

import blackdrive.cl.pagos_service.dto.PagosDto;
import blackdrive.cl.pagos_service.model.PagosModel;
import blackdrive.cl.pagos_service.service.PagosService;
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
        name = "Pagos",
        description = "Operaciones relacionadas con la gestión de pagos"
)
@RestController
@RequestMapping("/api/v6/pagos")
public class PagosController {

    @Autowired
    private PagosService pagosService;

    @Operation(
            summary = "Obtener todos los pagos",
            description = "Retorna el listado completo de pagos registrados, enriquecidos con datos del cliente via Feign"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "[{\"id\":1,\"monto\":50000,\"fecha\":\"2025-06-01\",\"metodoPago\":\"Transferencia\",\"cliente\":{\"id\":1,\"nombre\":\"Juan Pérez\",\"correo\":\"juan@gmail.com\"}}]"
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<PagosDto>> findAll() {
        return ResponseEntity.ok(pagosService.findAll());
    }

    @Operation(
            summary = "Obtener pago por ID",
            description = "Retorna los datos de un pago según su identificador, incluyendo información del cliente asociado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Pago encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "{\"id\":1,\"monto\":50000,\"fecha\":\"2025-06-01\",\"metodoPago\":\"Transferencia\",\"cliente\":{\"id\":1,\"nombre\":\"Juan Pérez\",\"correo\":\"juan@gmail.com\"}}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagosDto> findById(@PathVariable Long id) {
        PagosDto dto = pagosService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Registrar un nuevo pago",
            description = "Crea un nuevo pago en el sistema. El monto debe ser positivo y la fecha es obligatoria"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Pago registrado correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "\"Pago agregado correctamente\"")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos: monto negativo o campo obligatorio vacío",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"mensaje\":\"El monto ingresado no es válido: -5000\"}")
                    )
            )
    })
    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody PagosModel pago) {
        pagosService.save(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pago agregado correctamente");
    }

    @Operation(
            summary = "Eliminar pago",
            description = "Elimina un pago existente según su identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Pago eliminado correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "\"Pago eliminado correctamente\"")
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        pagosService.delete(id);
        return ResponseEntity.ok("Pago eliminado correctamente");
    }

    @Operation(
            summary = "Buscar pagos por método de pago",
            description = "Retorna los pagos filtrados por método de pago (ej: Transferencia, Efectivo, Tarjeta)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta realizada correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "[{\"id\":1,\"monto\":50000,\"fecha\":\"2025-06-01\",\"metodoPago\":\"Transferencia\",\"clienteId\":1}]"
                            )
                    )
            )
    })
    @GetMapping("/metodo/{metodoPago}")
    public ResponseEntity<?> findByMetodo(@PathVariable String metodoPago) {
        List<PagosModel> pagos = pagosService.findByMetodoPago(metodoPago);
        if (pagos.isEmpty()) {
            return ResponseEntity.ok("Nadie ha pagado en " + metodoPago + " por el momento");
        }
        return ResponseEntity.ok(pagos);
    }

    @Operation(
            summary = "Buscar pagos por rango de monto",
            description = "Retorna los pagos cuyo monto se encuentra entre un mínimo y un máximo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "[{\"id\":1,\"monto\":50000,\"fecha\":\"2025-06-01\",\"metodoPago\":\"Transferencia\",\"clienteId\":1}]"
                            )
                    )
            )
    })
    @GetMapping("/monto/{min}/{max}")
    public ResponseEntity<List<PagosModel>> findByMonto(@PathVariable Integer min, @PathVariable Integer max) {
        return ResponseEntity.ok(pagosService.findByMonto(min, max));
    }

    @Operation(
            summary = "Buscar pagos por cliente",
            description = "Retorna todos los pagos asociados a un cliente según su ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "[{\"id\":1,\"monto\":50000,\"fecha\":\"2025-06-01\",\"metodoPago\":\"Transferencia\",\"clienteId\":1}]"
                            )
                    )
            )
    })
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PagosModel>> findByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pagosService.findByCliente(clienteId));
    }
}