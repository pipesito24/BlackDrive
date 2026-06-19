package blackdrive.cl.sucursales_service.repository;

import blackdrive.cl.sucursales_service.model.SucursalesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalesRepository extends JpaRepository<SucursalesModel, Long> {
    List<SucursalesModel> findByCiudad(String ciudad);
    List<SucursalesModel> findByNombreContaining(String nombre);
}
