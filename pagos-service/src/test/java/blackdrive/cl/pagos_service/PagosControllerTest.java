package blackdrive.cl.pagos_service;

import blackdrive.cl.pagos_service.controller.PagosController;
import blackdrive.cl.pagos_service.dto.PagosDto;
import blackdrive.cl.pagos_service.exception.PagoNoEncontradoException;
import blackdrive.cl.pagos_service.model.PagosModel;
import blackdrive.cl.pagos_service.service.PagosService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagosController.class)
@DisplayName("Pruebas en la capa Controller de pagos")
class PagosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagosService pagosService;

    @Autowired
    private ObjectMapper objectMapper;

    private PagosModel pago;
    private PagosModel pagoSinId;
    private PagosDto pagoDTO;

    @BeforeEach
    void setUp() {
        pago = new PagosModel(1L, 50000, LocalDate.of(2026, 1, 1), "EFECTIVO", 1L);
        pagoSinId = new PagosModel(null, 50000, LocalDate.of(2026, 1, 1), "EFECTIVO", 1L);
        pagoDTO = new PagosDto();
        pagoDTO.setId(1L);
        pagoDTO.setMonto(50000);
        pagoDTO.setFecha(LocalDate.of(2026, 1, 1));
        pagoDTO.setMetodoPago("EFECTIVO");
    }

    @Test
    @DisplayName("GET /api/v6/pagos - Debería retornar 200 OK y la lista de pagos")
    public void testEndpointFindAll() throws Exception {
        when(pagosService.findAll()).thenReturn(List.of(pagoDTO));

        mockMvc.perform(get("/api/v6/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].monto").value(50000))
                .andExpect(jsonPath("$[0].metodoPago").value("EFECTIVO"));
    }

    @Test
    @DisplayName("POST /api/v6/pagos - Debería retornar 201 CREATED")
    void testEndpointSave() throws Exception {
        when(pagosService.save(any(PagosModel.class))).thenReturn(pago);

        mockMvc.perform(post("/api/v6/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoSinId)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/v6/pagos/{id} - Debería retornar 200 OK y el pago encontrado")
    void testEndpointFindById() throws Exception {
        when(pagosService.findById(1L)).thenReturn(pagoDTO);

        mockMvc.perform(get("/api/v6/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.monto").value(50000));
    }

    @Test
    @DisplayName("GET /api/v6/pagos/{id} - Debería retornar 404 cuando el pago no existe")
    void testEndpointFindByIdNoEncontrado() throws Exception {
        when(pagosService.findById(99L)).thenThrow(new PagoNoEncontradoException(99L));

        mockMvc.perform(get("/api/v6/pagos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v6/pagos/{id} - Debería retornar 200 OK")
    void testEndpointDelete() throws Exception {
        mockMvc.perform(delete("/api/v6/pagos/1"))
                .andExpect(status().isOk());
    }
}
