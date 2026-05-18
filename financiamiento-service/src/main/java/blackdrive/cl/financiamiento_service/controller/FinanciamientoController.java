package blackdrive.cl.financiamiento_service.controller;

import blackdrive.cl.financiamiento_service.dto.FinanciamientoDto;
import blackdrive.cl.financiamiento_service.model.FinanciamientoModel;
import blackdrive.cl.financiamiento_service.service.FinanciamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/financiamiento")
public class FinanciamientoController {

    @Autowired
    private FinanciamientoService financiamientoService;

    @GetMapping
    public ResponseEntity<List<FinanciamientoDto>> findAll() {
        return ResponseEntity.ok(financiamientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanciamientoDto> findById(@PathVariable Long id) {
        FinanciamientoDto dto = financiamientoService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody FinanciamientoModel financiamiento) {
        financiamientoService.save(financiamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body("Financiamiento agregado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        financiamientoService.delete(id);
        return ResponseEntity.ok("Financiamiento eliminado correctamente");
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<FinanciamientoModel>> findByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(financiamientoService.findByEstado(estado));
    }

    @GetMapping("/cuotas/{cuotas}")
    public ResponseEntity<?> findByCuotas(@PathVariable Integer cuotas) {
        List<FinanciamientoModel> resultado = financiamientoService.findByCuotas(cuotas);
        if (resultado.isEmpty()) {
            return ResponseEntity.ok("No existe usuario pagando " + cuotas + " cuotas");
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<FinanciamientoModel>> findByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(financiamientoService.findByCliente(clienteId));
    }
}
