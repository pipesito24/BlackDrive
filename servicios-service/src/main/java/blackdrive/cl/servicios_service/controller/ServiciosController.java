package blackdrive.cl.servicios_service.controller;

import blackdrive.cl.servicios_service.dto.ServiciosDto;
import blackdrive.cl.servicios_service.model.ServiciosModel;
import blackdrive.cl.servicios_service.service.ServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/servicios")
public class ServiciosController {

    @Autowired
    private ServiciosService serviciosService;

    @GetMapping
    public ResponseEntity<List<ServiciosDto>> findAll() {
        return ResponseEntity.ok(serviciosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiciosDto> findById(@PathVariable Long id) {
        ServiciosDto dto = serviciosService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody ServiciosModel servicio) {
        serviciosService.save(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body("Servicio agregado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        serviciosService.delete(id);
        return ResponseEntity.ok("Servicio eliminado correctamente");
    }
    @GetMapping("/tipo/{tipoServicio}")
    public ResponseEntity<List<ServiciosModel>> findByTipo(@PathVariable String tipoServicio) {
        return ResponseEntity.ok(serviciosService.findByTipo(tipoServicio));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ServiciosModel>> findByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(serviciosService.findByEstado(estado));
    }

    @GetMapping("/costo/{min}/{max}")
    public ResponseEntity<List<ServiciosModel>> findByCosto(@PathVariable Integer min, @PathVariable Integer max) {
        return ResponseEntity.ok(serviciosService.findByCosto(min, max));
    }
}