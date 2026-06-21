package blackdrive.cl.pagos_service;

import blackdrive.cl.pagos_service.clients.ClientesFeign;
import blackdrive.cl.pagos_service.dto.ClientesDto;
import blackdrive.cl.pagos_service.dto.PagosDto;
import blackdrive.cl.pagos_service.exception.MontoInvalidoException;
import blackdrive.cl.pagos_service.exception.PagoNoEncontradoException;
import blackdrive.cl.pagos_service.mapper.PagosMapper;
import blackdrive.cl.pagos_service.model.PagosModel;
import blackdrive.cl.pagos_service.repository.PagosRepository;
import blackdrive.cl.pagos_service.service.PagosService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para PagosService")
public class PagosServiceTest {

    @Mock
    private PagosRepository pagosRepository;

    @Mock
    private PagosMapper pagosMapper;

    @Mock
    private ClientesFeign clientesFeign;

    @InjectMocks
    private PagosService pagosService;

    private PagosModel pago;
    private PagosModel pagoSinId;
    private PagosDto pagoDTO;
    private ClientesDto clienteDTO;

    @BeforeEach
    public void setUp() {

        pago = new PagosModel(
                1L,
                50000,
                LocalDate.of(2026, 1, 1),
                "EFECTIVO",
                1L
        );

        pagoSinId = new PagosModel(
                null,
                50000,
                LocalDate.of(2026, 1, 1),
                "EFECTIVO",
                1L
        );

        pagoDTO = new PagosDto();
        pagoDTO.setMonto(50000);
        pagoDTO.setFecha(LocalDate.of(2026, 1, 1));
        pagoDTO.setMetodoPago("EFECTIVO");

        clienteDTO = new ClientesDto();
        clienteDTO.setNombre("Juan Pérez");
    }

    @Test
    @DisplayName("Debe listar todos los pagos con el nombre del cliente")
    public void listarTodos_deberiaRetornarListaDePagosDTO() {

        when(pagosRepository.findAll()).thenReturn(List.of(pago));
        when(pagosMapper.toListDTO(any(), any())).thenReturn(List.of(pagoDTO));

        List<PagosDto> resultado = pagosService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(50000, resultado.get(0).getMonto());

        verify(pagosRepository).findAll();
        verify(pagosMapper).toListDTO(any(), any());
    }

    @Test
    @DisplayName("Debe guardar un pago correctamente cuando el monto es válido")
    public void guardar_deberiaGuardarYRetornarPago() {

        when(pagosRepository.save(pagoSinId)).thenReturn(pago);

        PagosModel resultado = pagosService.save(pagoSinId);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(50000, resultado.getMonto());

        verify(pagosRepository).save(pagoSinId);
    }

    @Test
    @DisplayName("Debe lanzar MontoInvalidoException cuando el monto es menor o igual a cero")
    public void guardar_conMontoInvalido_deberiaLanzarMontoInvalidoException() {

        pagoSinId.setMonto(-100);

        MontoInvalidoException exception = assertThrows(
                MontoInvalidoException.class,
                () -> pagosService.save(pagoSinId)
        );

        assertEquals("El monto del pago no es válido: -100", exception.getMessage());

        verify(pagosRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe buscar un pago por ID cuando existe")
    public void buscarPorId_cuandoExiste_deberiaRetornarPagoDTO() {

        when(pagosRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(clientesFeign.buscarPorId(1L)).thenReturn(clienteDTO);
        when(pagosMapper.toDTO(pago, clienteDTO)).thenReturn(pagoDTO);

        PagosDto resultado = pagosService.findById(1L);

        assertNotNull(resultado);
        assertEquals(50000, resultado.getMonto());

        verify(pagosRepository).findById(1L);
        verify(pagosMapper).toDTO(pago, clienteDTO);
    }

    @Test
    @DisplayName("Debe lanzar PagoNoEncontradoException cuando el pago no existe")
    public void buscarPorId_cuandoNoExiste_deberiaLanzarPagoNoEncontradoException() {

        when(pagosRepository.findById(99L)).thenReturn(Optional.empty());

        PagoNoEncontradoException exception = assertThrows(
                PagoNoEncontradoException.class,
                () -> pagosService.findById(99L)
        );

        assertEquals("No existe un pago con id: 99", exception.getMessage());

        verify(pagosRepository).findById(99L);
        verifyNoInteractions(pagosMapper);
        verifyNoInteractions(clientesFeign);
    }

    @Test
    @DisplayName("Debe eliminar un pago por ID correctamente")
    public void eliminar_deberiaEliminarPagoPorId() {

        pagosService.delete(1L);

        verify(pagosRepository).deleteById(1L);
    }
}
