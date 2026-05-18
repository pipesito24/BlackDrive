package blackdrive.cl.vehiculos_service.clients;

import blackdrive.cl.vehiculos_service.dto.ClientesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes-service")
public interface ClientesFeign {
    @GetMapping("/api/v6/clientes/{id}")
    ClientesDto buscarPorId(@PathVariable Long id);
}
