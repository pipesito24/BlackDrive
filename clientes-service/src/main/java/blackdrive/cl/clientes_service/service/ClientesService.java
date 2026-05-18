package blackdrive.cl.clientes_service.service;

import blackdrive.cl.clientes_service.dto.ClientesDto;
import blackdrive.cl.clientes_service.mapper.ClientesMapper;
import blackdrive.cl.clientes_service.model.ClientesModel;
import blackdrive.cl.clientes_service.repository.ClientesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientesService {

    private static final Logger log = LoggerFactory.getLogger(ClientesService.class);

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private ClientesMapper clientesMapper;

    public List<ClientesDto> findAll() {
        log.info("Consultando todos los clientes");
        return clientesMapper.toListDTO(clientesRepository.findAll());
    }

    public ClientesDto findById(Long id) {
        log.info("Buscando cliente con id: {}", id);
        ClientesModel cliente = clientesRepository.findById(id).orElse(null);
        if (cliente == null) log.warn("Cliente con id {} no encontrado", id);
        return clientesMapper.toDTO(cliente);
    }

    public ClientesModel save(ClientesModel cliente) {
        if (cliente.getSueldo() != null && cliente.getSueldo() < 0) {
            log.error("Intento de guardar cliente con sueldo negativo: {}", cliente.getSueldo());
            throw new IllegalArgumentException("El sueldo no puede ser negativo");
        }
        log.info("Guardando nuevo cliente: {}", cliente.getNombre());
        return clientesRepository.save(cliente);
    }

    public void delete(Long id) {
        log.info("Eliminando cliente con id: {}", id);
        clientesRepository.deleteById(id);
    }

    public ClientesModel findByEmail(String email) {
        log.info("Buscando cliente por email: {}", email);
        return clientesRepository.findByEmail(email).orElse(null);
    }

    public List<ClientesModel> findBySueldo(Integer min, Integer max) {
        log.info("Buscando clientes con sueldo entre {} y {}", min, max);
        return clientesRepository.findBySueldoBetween(min, max);
    }

    public List<ClientesModel> findByNombre(String nombre) {
        log.info("Buscando clientes con nombre: {}", nombre);
        return clientesRepository.findByNombreContaining(nombre);
    }
}