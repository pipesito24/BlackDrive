package blackdrive.cl.pagos_service.service;

import blackdrive.cl.pagos_service.clients.ClientesFeign;
import blackdrive.cl.pagos_service.dto.ClientesDto;
import blackdrive.cl.pagos_service.dto.PagosDto;
import blackdrive.cl.pagos_service.mapper.PagosMapper;
import blackdrive.cl.pagos_service.model.PagosModel;
import blackdrive.cl.pagos_service.repository.PagosRepository;
import blackdrive.cl.pagos_service.exception.MontoInvalidoException;
import blackdrive.cl.pagos_service.exception.PagoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagosService {

    private static final Logger log = LoggerFactory.getLogger(PagosService.class);

    @Autowired
    private PagosRepository pagosRepository;
    @Autowired
    private PagosMapper pagosMapper;
    @Autowired
    private ClientesFeign clientesFeign;

    private ClientesDto buscarCliente(Long id) {
        try {
            return clientesFeign.buscarPorId(id);
        } catch (Exception e) {
            log.warn("No se pudo obtener cliente con id {}: {}", id, e.getMessage());
            return null;
        }
    }

    public List<PagosDto> findAll() {
        log.info("Consultando todos los pagos");
        return pagosMapper.toListDTO(pagosRepository.findAll(), id -> buscarCliente(id));
    }

    public PagosDto findById(Long id) {
        log.info("Buscando pago con id: {}", id);
        PagosModel pago = pagosRepository.findById(id)
                .orElseThrow(() -> new PagoNoEncontradoException(id));
        ClientesDto cliente = buscarCliente(pago.getClienteId());
        return pagosMapper.toDTO(pago, cliente);
    }

    public PagosModel save(PagosModel pago) {
        if (pago.getMonto() != null && pago.getMonto() <= 0) {
            log.error("Monto inválido: {}", pago.getMonto());
            throw new MontoInvalidoException(pago.getMonto());
        }
        log.info("Guardando pago de monto: {}", pago.getMonto());
        return pagosRepository.save(pago);
    }

    public void delete(Long id) {
        log.info("Eliminando pago con id: {}", id);
        pagosRepository.deleteById(id);
    }

    public List<PagosModel> findByMetodoPago(String metodoPago) {
        log.info("Buscando pagos por método: {}", metodoPago);
        return pagosRepository.findByMetodoPago(metodoPago);
    }

    public List<PagosModel> findByMonto(Integer min, Integer max) {
        log.info("Buscando pagos con monto entre {} y {}", min, max);
        return pagosRepository.findByMontoBetween(min, max);
    }

    public List<PagosModel> findByCliente(Long clienteId) {
        log.info("Buscando pagos del cliente: {}", clienteId);
        return pagosRepository.findByClienteId(clienteId);
    }
}
