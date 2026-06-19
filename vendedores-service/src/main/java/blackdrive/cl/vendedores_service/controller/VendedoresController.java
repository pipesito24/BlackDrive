package blackdrive.cl.vendedores_service.controller;

import blackdrive.cl.vendedores_service.dto.VendedoresDto;
import blackdrive.cl.vendedores_service.model.VendedoresModel;
import blackdrive.cl.vendedores_service.service.VendedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/vendedores")
public class VendedoresController {

    @Autowired
    private VendedoresService vendedoresService;

    @GetMapping
    public ResponseEntity<List<VendedoresDto>> findAll() {
        return ResponseEntity.ok(vendedoresService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedoresDto> findById(@PathVariable Long id) {
        VendedoresDto dto = vendedoresService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody VendedoresModel vendedor) {
        vendedoresService.save(vendedor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vendedor agregado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        vendedoresService.delete(id);
        return ResponseEntity.ok("Vendedor eliminado correctamente");
    }
    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<List<VendedoresModel>> findBySucursal(@PathVariable Long sucursalId) {
        return ResponseEntity.ok(vendedoresService.findBySucursal(sucursalId));
    }

    @GetMapping("/comision/{min}/{max}")
    public ResponseEntity<List<VendedoresModel>> findByComision(
            @PathVariable Double min,
            @PathVariable Double max) {
        return ResponseEntity.ok(vendedoresService.findByComision(min, max));
    }
}
