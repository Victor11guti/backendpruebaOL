package com.prueba.ol.service;

import com.prueba.ol.DTO.ComercianteDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ComercianteService {
    Page<ComercianteDTO> listar(String nombre, String estado, String fecha, Pageable pageable);
    Optional<ComercianteDTO> buscarPorId(Long id);
    ComercianteDTO crear(ComercianteDTO dto, String usuario);
    ComercianteDTO actualizar(Long id, ComercianteDTO dto, String usuario);
    void eliminar(Long id);
    ComercianteDTO cambiarEstado(Long id, String estado, String usuario);
    Resource exportarCSV(); // Exportar comerciantes activos a CSV con separador |
}
