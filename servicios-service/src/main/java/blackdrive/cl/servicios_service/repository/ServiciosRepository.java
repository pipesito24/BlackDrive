package blackdrive.cl.servicios_service.repository;

import blackdrive.cl.servicios_service.model.ServiciosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiciosRepository extends JpaRepository<ServiciosModel, Long> {
    List<ServiciosModel> findByTipoServicio(String tipoServicio);
    List<ServiciosModel> findByEstado(String estado);
    List<ServiciosModel> findByCostoBetween(Integer min, Integer max);
}