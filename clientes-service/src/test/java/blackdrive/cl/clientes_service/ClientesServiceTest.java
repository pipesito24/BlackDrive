package blackdrive.cl.clientes_service;

import blackdrive.cl.clientes_service.dto.ClientesDto;
import blackdrive.cl.clientes_service.exception.ClienteNoEncontradoException;
import blackdrive.cl.clientes_service.exception.SueldoInvalidoException;
import blackdrive.cl.clientes_service.mapper.ClientesMapper;
import blackdrive.cl.clientes_service.model.ClientesModel;
import blackdrive.cl.clientes_service.repository.ClientesRepository;
import blackdrive.cl.clientes_service.service.ClientesService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para ClientesService")
public class ClientesServiceTest {

    @Mock
    private ClientesRepository clientesRepository;

    @Mock
    private ClientesMapper clientesMapper;

    @InjectMocks
    private ClientesService clientesService;

    private ClientesModel cliente;
    private ClientesModel clienteSinId;
    private ClientesDto clienteDTO;

    @BeforeEach
    public void setUp() {

        cliente = new ClientesModel(
                1L,
                "Juan Pérez",
                "juan@gmail.com",
                "Av. Siempre Viva 123",
                500000
        );

        clienteSinId = new ClientesModel(
                null,
                "Juan Pérez",
                "juan@gmail.com",
                "Av. Siempre Viva 123",
                500000
        );

        clienteDTO = new ClientesDto(1L, "Juan Pérez", "juan@gmail.com", 500000);
    }

    @Test
    @DisplayName("Debe listar todos los clientes correctamente")
    public void listarTodos_deberiaRetornarListaDeClientesDTO() {

        when(clientesRepository.findAll()).thenReturn(List.of(cliente));
        when(clientesMapper.toListDTO(List.of(cliente))).thenReturn(List.of(clienteDTO));

        List<ClientesDto> resultado = clientesService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());

        verify(clientesRepository).findAll();
        verify(clientesMapper).toListDTO(List.of(cliente));
    }

    @Test
    @DisplayName("Debe guardar un cliente correctamente cuando el sueldo es válido")
    public void guardar_deberiaGuardarYRetornarCliente() {

        when(clientesRepository.save(clienteSinId)).thenReturn(cliente);

        ClientesModel resultado = clientesService.save(clienteSinId);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombre());
        assertEquals(500000, resultado.getSueldo());

        verify(clientesRepository).save(clienteSinId);
    }

    @Test
    @DisplayName("Debe lanzar SueldoInvalidoException cuando el sueldo es negativo")
    public void guardar_conSueldoNegativo_deberiaLanzarSueldoInvalidoException() {

        clienteSinId.setSueldo(-500);

        SueldoInvalidoException exception = assertThrows(
                SueldoInvalidoException.class,
                () -> clientesService.save(clienteSinId)
        );

        assertEquals("El sueldo ingresado no es válido: -500", exception.getMessage());

        verify(clientesRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe buscar un cliente por ID cuando existe")
    public void buscarPorId_cuandoExiste_deberiaRetornarClienteDTO() {

        when(clientesRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clientesMapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClientesDto resultado = clientesService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombre());

        verify(clientesRepository).findById(1L);
        verify(clientesMapper).toDTO(cliente);
    }

    @Test
    @DisplayName("Debe lanzar ClienteNoEncontradoException cuando el cliente no existe")
    public void buscarPorId_cuandoNoExiste_deberiaLanzarClienteNoEncontradoException() {

        when(clientesRepository.findById(99L)).thenReturn(Optional.empty());

        ClienteNoEncontradoException exception = assertThrows(
                ClienteNoEncontradoException.class,
                () -> clientesService.findById(99L)
        );

        verify(clientesRepository).findById(99L);
        verifyNoInteractions(clientesMapper);
    }

    @Test
    @DisplayName("Debe eliminar un cliente por ID correctamente")
    public void eliminar_deberiaEliminarClientePorId() {

        clientesService.delete(1L);

        verify(clientesRepository).deleteById(1L);
    }
}
