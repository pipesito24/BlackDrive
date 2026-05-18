package blackdrive.cl.sucursales_service.controller;

import blackdrive.cl.sucursales_service.dto.SucursalesDto;
import blackdrive.cl.sucursales_service.model.SucursalesModel;
import blackdrive.cl.sucursales_service.service.SucursalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/sucursales")
public class SucursalesController {

    @Autowired
    private SucursalesService sucursalesService;

    @GetMapping
    public ResponseEntity<List<SucursalesDto>> findAll() {
        return ResponseEntity.ok(sucursalesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalesDto> findById(@PathVariable Long id) {
        SucursalesDto dto = sucursalesService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody SucursalesModel sucursal) {
        sucursalesService.save(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sucursal agregada correctamente");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        sucursalesService.delete(id);
        return ResponseEntity.ok("Sucursal eliminada correctamente");
    }
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<SucursalesModel>> findByCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(sucursalesService.findByCiudad(ciudad));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<SucursalesModel>> findByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(sucursalesService.findByNombre(nombre));
    }
}
