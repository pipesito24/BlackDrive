package blackdrive.cl.vendedores_service.repository;

import blackdrive.cl.vendedores_service.model.VendedoresModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendedoresRepository extends JpaRepository<VendedoresModel, Long> {
    List<VendedoresModel> findBySucursalId(Long sucursalId);
    List<VendedoresModel> findByComisionBetween(Double min, Double max);
}
