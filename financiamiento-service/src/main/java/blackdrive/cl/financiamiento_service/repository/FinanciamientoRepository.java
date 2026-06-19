package blackdrive.cl.financiamiento_service.repository;

import blackdrive.cl.financiamiento_service.model.FinanciamientoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinanciamientoRepository extends JpaRepository<FinanciamientoModel, Long> {
    List<FinanciamientoModel> findByEstado(String estado);
    List<FinanciamientoModel> findByCuotas(Integer cuotas);
    List<FinanciamientoModel> findByClienteId(Long clienteId);
}
