package blackdrive.cl.pagos_service.controller;

import blackdrive.cl.pagos_service.dto.PagosDto;
import blackdrive.cl.pagos_service.model.PagosModel;
import blackdrive.cl.pagos_service.service.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/pagos")
public class PagosController {

    @Autowired
    private PagosService pagosService;

    @GetMapping
    public ResponseEntity<List<PagosDto>> findAll() {
        return ResponseEntity.ok(pagosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagosDto> findById(@PathVariable Long id) {
        PagosDto dto = pagosService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody PagosModel pago) {
        pagosService.save(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pago agregado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        pagosService.delete(id);
        return ResponseEntity.ok("Pago eliminado correctamente");
    }
    @GetMapping("/metodo/{metodoPago}")
    public ResponseEntity<?> findByMetodo(@PathVariable String metodoPago) {
        List<PagosModel> pagos = pagosService.findByMetodoPago(metodoPago);
        if (pagos.isEmpty()) {
            return ResponseEntity.ok("Nadie ha pagado en " + metodoPago + " por el momento");
        }
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/monto/{min}/{max}")
    public ResponseEntity<List<PagosModel>> findByMonto(@PathVariable Integer min, @PathVariable Integer max) {
        return ResponseEntity.ok(pagosService.findByMonto(min, max));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PagosModel>> findByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pagosService.findByCliente(clienteId));
    }
}