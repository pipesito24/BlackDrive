package blackdrive.cl.clientes_service;

import blackdrive.cl.clientes_service.controller.ClientesController;
import blackdrive.cl.clientes_service.dto.ClientesDto;
import blackdrive.cl.clientes_service.exception.ClienteNoEncontradoException;
import blackdrive.cl.clientes_service.exception.SueldoInvalidoException;
import blackdrive.cl.clientes_service.model.ClientesModel;
import blackdrive.cl.clientes_service.service.ClientesService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientesController.class)
@DisplayName("Pruebas en la capa Controller de clientes")
class ClientesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientesService clientesService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientesModel cliente;
    private ClientesModel clienteSinId;
    private ClientesDto clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new ClientesModel(1L, "Juan Pérez", "juan@gmail.com", "Av. Siempre Viva 123", 500000);
        clienteSinId = new ClientesModel(null, "Juan Pérez", "juan@gmail.com", "Av. Siempre Viva 123", 500000);
        clienteDTO = new ClientesDto(1L, "Juan Pérez", "juan@gmail.com", 500000);
    }

    @Test
    @DisplayName("GET /api/v6/clientes - Debería retornar 200 OK y la lista de clientes")
    public void testEndpointFindAll() throws Exception {
        when(clientesService.findAll()).thenReturn(List.of(clienteDTO));

        mockMvc.perform(get("/api/v6/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$[0].correo").value("juan@gmail.com"))
                .andExpect(jsonPath("$[0].sueldo").value(500000));
    }

    @Test
    @DisplayName("POST /api/v6/clientes - Debería retornar 201 CREATED")
    void testEndpointSave() throws Exception {
        when(clientesService.save(any(ClientesModel.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/v6/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteSinId)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/v6/clientes/{id} - Debería retornar 200 OK y el cliente encontrado")
    void testEndpointFindById() throws Exception {
        when(clientesService.findById(1L)).thenReturn(clienteDTO);

        mockMvc.perform(get("/api/v6/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    @DisplayName("GET /api/v6/clientes/{id} - Debería retornar 404 cuando el cliente no existe")
    void testEndpointFindByIdNoEncontrado() throws Exception {
        when(clientesService.findById(99L)).thenThrow(new ClienteNoEncontradoException(99L));

        mockMvc.perform(get("/api/v6/clientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v6/clientes/{id} - Debería retornar 200 OK")
    void testEndpointDelete() throws Exception {
        mockMvc.perform(delete("/api/v6/clientes/1"))
                .andExpect(status().isOk());
    }
}
