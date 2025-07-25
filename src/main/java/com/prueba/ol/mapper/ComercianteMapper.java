package com.prueba.ol.mapper;

import org.springframework.stereotype.Component;

import com.prueba.ol.DTO.ComercianteDTO;
import com.prueba.ol.entity.Comerciante;

@Component
public class ComercianteMapper {
    public ComercianteDTO toDTO(Comerciante c) {
        ComercianteDTO dto = new ComercianteDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setMunicipio(c.getMunicipio());
        dto.setTelefono(c.getTelefono());
        dto.setCorreo(c.getCorreo());
        dto.setFechaRegistro(c.getFechaRegistro());
        dto.setEstado(c.getEstado());
        return dto;
    }

    public Comerciante toEntity(ComercianteDTO dto) {
        Comerciante c = new Comerciante();
        c.setId(dto.getId());
        c.setNombre(dto.getNombre());
        c.setMunicipio(dto.getMunicipio());
        c.setTelefono(dto.getTelefono());
        c.setCorreo(dto.getCorreo());
        c.setFechaRegistro(dto.getFechaRegistro());
        c.setEstado(dto.getEstado());
        return c;
    }
}