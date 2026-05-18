package blackdrive.cl.inventario_service.controller;

import blackdrive.cl.inventario_service.dto.InventarioDto;
import blackdrive.cl.inventario_service.model.InventarioModel;
import blackdrive.cl.inventario_service.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InventarioDto>> findAll() {
        return ResponseEntity.ok(inventarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioDto> findById(@PathVariable Long id) {
        InventarioDto dto = inventarioService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody InventarioModel inventario) {
        inventarioService.save(inventario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ítem de inventario agregado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        inventarioService.delete(id);
        return ResponseEntity.ok("Ítem de inventario eliminado correctamente");
    }
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> findByCategoria(@PathVariable String categoria) {
        List<InventarioModel> inventario = inventarioService.findByCategoria(categoria);
        if (inventario.isEmpty()) {
            return ResponseEntity.ok("Categoría " + categoria + " no disponible en stock");
        }
        return ResponseEntity.ok(inventario);
    }

    @GetMapping("/stock-bajo/{stock}")
    public ResponseEntity<List<InventarioModel>> findByStockBajo(@PathVariable Integer stock) {
        return ResponseEntity.ok(inventarioService.findByStockBajo(stock));
    }

    @GetMapping("/precio/{min}/{max}")
    public ResponseEntity<List<InventarioModel>> findByPrecio(@PathVariable Integer min, @PathVariable Integer max) {
        return ResponseEntity.ok(inventarioService.findByPrecio(min, max));
    }
}