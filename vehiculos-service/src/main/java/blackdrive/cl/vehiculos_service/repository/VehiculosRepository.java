package blackdrive.cl.vehiculos_service.repository;

import blackdrive.cl.vehiculos_service.model.VehiculosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculosRepository extends JpaRepository<VehiculosModel, Long> {
    List<VehiculosModel> findByMarca(String marca);
    List<VehiculosModel> findByAno(Integer ano);
    List<VehiculosModel> findByPrecioBetween(Integer min, Integer max);
}
