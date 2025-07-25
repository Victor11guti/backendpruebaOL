package com.prueba.ol;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.ol.DTO.ComercianteDTO;
import com.prueba.ol.service.ComercianteService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ComercianteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComercianteService comercianteService;

    @Autowired
    private ObjectMapper objectMapper;

    private ComercianteDTO crearMock() {
        ComercianteDTO dto = new ComercianteDTO();
        dto.setId(1L);
        dto.setNombre("Prueba");
        dto.setMunicipio("Bogotá");
        dto.setTelefono("123456789");
        dto.setCorreo("correo@ejemplo.com");
        dto.setEstado("Activo");
        dto.setFechaRegistro(LocalDate.now());
        return dto;
    }

    @Test
    @WithMockUser
    void crearComerciante() throws Exception {
        ComercianteDTO mock = crearMock();
        when(comercianteService.crear(any(), anyString())).thenReturn(mock);

        mockMvc.perform(post("/api/comerciantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("correo@ejemplo.com"));
    }

    @Test
    @WithMockUser
    void buscarPorId_ok() throws Exception {
        when(comercianteService.buscarPorId(1L)).thenReturn(Optional.of(crearMock()));

        mockMvc.perform(get("/api/comerciantes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.municipio").value("Bogotá"));
    }

    @Test
    @WithMockUser
    void buscarPorId_notFound() throws Exception {
        when(comercianteService.buscarPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/comerciantes/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void cambiarEstado() throws Exception {
        ComercianteDTO mock = crearMock();
        mock.setEstado("Inactivo");

        when(comercianteService.cambiarEstado(eq(1L), eq("Inactivo"), anyString())).thenReturn(mock);

        mockMvc.perform(patch("/api/comerciantes/1/estado?estado=Inactivo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("Inactivo"));
    }

    @Test
    @WithMockUser
    void actualizarComerciante() throws Exception {
        ComercianteDTO mock = crearMock();
        when(comercianteService.actualizar(eq(1L), any(), anyString())).thenReturn(mock);

        mockMvc.perform(put("/api/comerciantes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("Activo"));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRADOR")
    void eliminarComerciante() throws Exception {
        doNothing().when(comercianteService).eliminar(1L);

        mockMvc.perform(delete("/api/comerciantes/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMINISTRADOR")
    void exportarCSV() throws Exception {
        Resource resource = new ByteArrayResource("id,nombre,correo".getBytes());

        when(comercianteService.exportarCSV()).thenReturn(resource);

        mockMvc.perform(get("/api/comerciantes/exportar"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=comerciantes.csv"));
    }
}
