package blackdrive.cl.facturas_service.clients;

import blackdrive.cl.facturas_service.dto.PagosDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pagos-service")
public interface PagosFeign {
    @GetMapping("/api/v6/pagos/{id}")
    PagosDto buscarPorId(@PathVariable Long id);
}