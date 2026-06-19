package blackdrive.cl.servicios_service.clients;

import blackdrive.cl.servicios_service.dto.VehiculosDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehiculos-service")
public interface VehiculosFeign {
    @GetMapping("/api/v6/vehiculos/{id}")
    VehiculosDto buscarPorId(@PathVariable Long id);
}
