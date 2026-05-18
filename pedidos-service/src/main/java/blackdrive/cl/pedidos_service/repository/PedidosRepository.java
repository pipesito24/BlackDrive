package blackdrive.cl.pedidos_service.repository;

import blackdrive.cl.pedidos_service.model.PedidosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidosRepository extends JpaRepository<PedidosModel, Long> {
    List<PedidosModel> findByEstado(String estado);
    List<PedidosModel> findByClienteId(Long clienteId);
    List<PedidosModel> findByPrecioUnitarioBetween(Integer min, Integer max);
}
