package com.prueba.seguridad;

import com.prueba.ol.DTO.ComercianteDTO;
import com.prueba.ol.service.ComercianteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(ComercianteController.class)
public class ComercianteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComercianteService comercianteService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testBuscarComerciantePorId() throws Exception {
        ComercianteDTO dto = new ComercianteDTO();
        dto.setId(1L);
        dto.setNombre("Juan Pérez");

        Mockito.when(comercianteService.buscar(anyLong())).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/comerciantes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }
}