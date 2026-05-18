package blackdrive.cl.vendedores_service.clients;

import blackdrive.cl.vendedores_service.dto.SucursalesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sucursales-service")
public interface SucursalFeign {
    @GetMapping("/api/v6/sucursales/{id}")
    SucursalesDto buscarPorId(@PathVariable Long id);
}
