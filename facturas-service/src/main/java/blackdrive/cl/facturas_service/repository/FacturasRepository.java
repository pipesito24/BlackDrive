package blackdrive.cl.facturas_service.repository;

import blackdrive.cl.facturas_service.model.FacturasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturasRepository extends JpaRepository<FacturasModel, Long> {
    List<FacturasModel> findByEstado(String estado);
    List<FacturasModel> findByClienteId(Long clienteId);
    List<FacturasModel> findByMontoTotalBetween(Integer min, Integer max);
}
