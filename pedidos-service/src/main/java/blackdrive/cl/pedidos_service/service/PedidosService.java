package blackdrive.cl.pedidos_service.service;

import blackdrive.cl.pedidos_service.clients.ClientesFeign;
import blackdrive.cl.pedidos_service.dto.ClientesDto;
import blackdrive.cl.pedidos_service.dto.PedidosDto;
import blackdrive.cl.pedidos_service.mapper.PedidosMapper;
import blackdrive.cl.pedidos_service.model.PedidosModel;
import blackdrive.cl.pedidos_service.repository.PedidosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosService {

    private static final Logger log = LoggerFactory.getLogger(PedidosService.class);

    @Autowired
    private PedidosRepository pedidosRepository;
    @Autowired
    private PedidosMapper pedidosMapper;
    @Autowired
    private ClientesFeign clienteFeign;

    private ClientesDto buscarCliente(Long id) {
        try {
            return clienteFeign.buscarPorId(id);
        } catch (Exception e) {
            log.warn("No se pudo obtener cliente con id {}: {}", id, e.getMessage());
            return null;
        }
    }

    public List<PedidosDto> findAll() {
        log.info("Consultando todos los pedidos");
        return pedidosMapper.toListDTO(pedidosRepository.findAll(), id -> buscarCliente(id));
    }

    public PedidosDto findById(Long id) {
        log.info("Buscando pedido con id: {}", id);
        PedidosModel pedido = pedidosRepository.findById(id).orElse(null);
        if (pedido == null) {
            log.warn("Pedido con id {} no encontrado", id);
            return null;
        }
        ClientesDto cliente = buscarCliente(pedido.getClienteId());
        return pedidosMapper.toDTO(pedido, cliente);
    }

    public PedidosModel save(PedidosModel pedido) {
        if (pedido.getCantidad() != null && pedido.getCantidad() <= 0) {
            log.error("Cantidad inválida: {}", pedido.getCantidad());
            throw new IllegalArgumentException("La cantidad del pedido debe ser mayor a cero");
        }
        log.info("Guardando pedido: {}", pedido.getDescripcion());
        return pedidosRepository.save(pedido);
    }

    public void delete(Long id) {
        log.info("Eliminando pedido con id: {}", id);
        pedidosRepository.deleteById(id);
    }

    public List<PedidosModel> findByEstado(String estado) {
        log.info("Buscando pedidos por estado: {}", estado);
        return pedidosRepository.findByEstado(estado);
    }

    public List<PedidosModel> findByCliente(Long clienteId) {
        log.info("Buscando pedidos del cliente: {}", clienteId);
        return pedidosRepository.findByClienteId(clienteId);
    }

    public List<PedidosModel> findByPrecio(Integer min, Integer max) {
        log.info("Buscando pedidos con precio entre {} y {}", min, max);
        return pedidosRepository.findByPrecioUnitarioBetween(min, max);
    }
}