package blackdrive.cl.clientes_service.repository;

import blackdrive.cl.clientes_service.model.ClientesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientesRepository extends JpaRepository<ClientesModel, Long> {
    Optional<ClientesModel> findByCorreo(String correo);
    List<ClientesModel> findBySueldoBetween(Integer min, Integer max);
    List<ClientesModel> findByNombreContaining(String nombre);

    String correo(String correo);
}
