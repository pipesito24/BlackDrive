package blackdrive.cl.clientes_service.controller;

import blackdrive.cl.clientes_service.dto.ClientesDto;
import blackdrive.cl.clientes_service.model.ClientesModel;
import blackdrive.cl.clientes_service.service.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

    @GetMapping
    public ResponseEntity<List<ClientesDto>> findAll() {
        return ResponseEntity.ok(clientesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientesDto> findById(@PathVariable Long id) {
        ClientesDto dto = clientesService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody ClientesModel cliente) {
        clientesService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente agregado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        clientesService.delete(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<ClientesModel> findByEmail(@PathVariable String email) {
        ClientesModel cliente = clientesService.findByEmail(email);
        if (cliente == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/sueldo/{min}/{max}")
    public ResponseEntity<List<ClientesModel>> findBySueldo(@PathVariable Integer min, @PathVariable Integer max) {
        return ResponseEntity.ok(clientesService.findBySueldo(min, max));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ClientesModel>> findByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(clientesService.findByNombre(nombre));
    }
}
