package blackdrive.cl.vehiculos_service.controller;

import blackdrive.cl.vehiculos_service.dto.VehiculosDto;
import blackdrive.cl.vehiculos_service.model.VehiculosModel;
import blackdrive.cl.vehiculos_service.service.VehiculosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/v6/vehiculos")
public class VehiculosController {

    @Autowired
    private VehiculosService vehiculosService;

    @GetMapping
    public ResponseEntity<List<VehiculosDto>> findAll() {
        return ResponseEntity.ok(vehiculosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculosDto> findById(@PathVariable Long id) {
        VehiculosDto dto = vehiculosService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody VehiculosModel vehiculo) {
        vehiculosService.save(vehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vehículo agregado correctamente");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        vehiculosService.delete(id);
        return ResponseEntity.ok("Vehículo eliminado correctamente");
    }
    @GetMapping("/marca/{marca}")
    public ResponseEntity<?> findByMarca(@PathVariable String marca) {
        List<VehiculosModel> vehiculos = vehiculosService.findByMarca(marca);
        if (vehiculos.isEmpty()) {
            return ResponseEntity.ok("Marca " + marca + " no disponible por el momento");
        }
        return ResponseEntity.ok(vehiculos);
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<List<VehiculosModel>> findByAno(@PathVariable Integer ano) {
        return ResponseEntity.ok(vehiculosService.findByAno(ano));
    }

    @GetMapping("/precio/{min}/{max}")
    public ResponseEntity<List<VehiculosModel>> findByPrecio(@PathVariable Integer min, @PathVariable Integer max) {
        return ResponseEntity.ok(vehiculosService.findByPrecio(min, max));
    }


}
