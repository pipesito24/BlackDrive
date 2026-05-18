package blackdrive.cl.facturas_service.controller;

import blackdrive.cl.facturas_service.dto.FacturasDto;
import blackdrive.cl.facturas_service.model.FacturasModel;
import blackdrive.cl.facturas_service.service.FacturasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/facturas")
public class FacturasController {

    @Autowired
    private FacturasService facturasService;

    @GetMapping
    public ResponseEntity<List<FacturasDto>> findAll() {
        return ResponseEntity.ok(facturasService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturasDto> findById(@PathVariable Long id) {
        FacturasDto dto = facturasService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody FacturasModel factura) {
        facturasService.save(factura);
        return ResponseEntity.status(HttpStatus.CREATED).body("Factura agregada correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        facturasService.delete(id);
        return ResponseEntity.ok("Factura eliminada correctamente");
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<FacturasModel>> findByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(facturasService.findByEstado(estado));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<FacturasModel>> findByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(facturasService.findByCliente(clienteId));
    }

    @GetMapping("/monto/{min}/{max}")
    public ResponseEntity<List<FacturasModel>> findByMonto(@PathVariable Integer min, @PathVariable Integer max) {
        return ResponseEntity.ok(facturasService.findByMonto(min, max));
    }
}
