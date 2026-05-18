package blackdrive.cl.pedidos_service.controller;

import blackdrive.cl.pedidos_service.dto.PedidosDto;
import blackdrive.cl.pedidos_service.model.PedidosModel;
import blackdrive.cl.pedidos_service.service.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/pedidos")
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @GetMapping
    public ResponseEntity<List<PedidosDto>> findAll() {
        return ResponseEntity.ok(pedidosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidosDto> findById(@PathVariable Long id) {
        PedidosDto dto = pedidosService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody PedidosModel pedido) {
        pedidosService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido agregado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        pedidosService.delete(id);
        return ResponseEntity.ok("Pedido eliminado correctamente");
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidosModel>> findByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(pedidosService.findByEstado(estado));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidosModel>> findByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidosService.findByCliente(clienteId));
    }

    @GetMapping("/precio/{min}/{max}")
    public ResponseEntity<List<PedidosModel>> findByPrecio(@PathVariable Integer min, @PathVariable Integer max) {
        return ResponseEntity.ok(pedidosService.findByPrecio(min, max));
    }
}