package blackdrive.cl.pagos_service.repository;

import blackdrive.cl.pagos_service.model.PagosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagosRepository extends JpaRepository<PagosModel, Long> {
    List<PagosModel> findByMetodoPago(String metodoPago);
    List<PagosModel> findByMontoBetween(Integer min, Integer max);
    List<PagosModel> findByClienteId(Long clienteId);
}
