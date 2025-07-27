package com.prueba.ol.service.impl;

import com.prueba.ol.DTO.ComercianteDTO;
import com.prueba.ol.DTO.ComercianteReporteDTO;
import com.prueba.ol.entity.Comerciante;
import com.prueba.ol.mapper.ComercianteMapper;
import com.prueba.ol.repository.ComercianteRepository;
import com.prueba.ol.service.ComercianteService;

import jakarta.transaction.Transactional;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import oracle.jdbc.OracleTypes;
import javax.sql.DataSource;
import java.sql.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ComercianteServiceImpl implements ComercianteService {

    private final ComercianteRepository repo;
    private final ComercianteMapper mapper;
    private final DataSource dataSource;

    public ComercianteServiceImpl(ComercianteRepository repo, ComercianteMapper mapper, DataSource dataSource) {
        this.repo = repo;
        this.mapper = mapper;
        this.dataSource = dataSource;
    }

    private List<ComercianteReporteDTO> obtenerComerciantesDesdeFuncion() {
        List<ComercianteReporteDTO> lista = new java.util.ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{ ? = call pkg_reportes.comerciantes_activos }")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    ComercianteReporteDTO dto = new ComercianteReporteDTO();
                    dto.setNombre(rs.getString("nombre_razon_social"));
                    dto.setMunicipio(rs.getString("municipio"));
                    dto.setTelefono(rs.getString("telefono"));
                    dto.setCorreo(rs.getString("correo_electronico"));
                    dto.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                    dto.setEstado(rs.getString("estado"));
                    dto.setCantidadEstablecimientos(rs.getInt("cantidad_establecimientos"));
                    dto.setTotalIngresos(rs.getBigDecimal("total_ingresos"));
                    dto.setTotalEmpleados(rs.getInt("total_empleados"));
                    lista.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Puedes usar un logger en producción
        }
        return lista;
    }

    @Override
    public Page<ComercianteDTO> listar(String nombre, String estado, String fecha, Pageable pageable) {
        Specification<Comerciante> spec = (root, query, cb) -> cb.conjunction();
        if (nombre != null && !nombre.isEmpty())
            spec = spec.and((r, q, cb) -> cb.like(cb.lower(r.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        if (estado != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("estado"), estado));
        if (fecha != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("fechaRegistro"), LocalDate.parse(fecha)));
        return repo.findAll(spec, pageable).map(mapper::toDTO);
    }

    @Override
    public Optional<ComercianteDTO> buscarPorId(Long id) {
        return repo.findById(id).map(mapper::toDTO);
    }

    @Override
    @Transactional
    public ComercianteDTO crear(ComercianteDTO dto, String usuario) {
        Comerciante entity = mapper.toEntity(dto);
        entity.setFechaRegistro(LocalDate.now());
        entity.setFechaActualizacion(LocalDate.now());
        entity.setUsuario(usuario);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    @Transactional
    public ComercianteDTO actualizar(Long id, ComercianteDTO dto, String usuario) {
        Comerciante c = repo.findById(id).orElseThrow();
        c.setNombre(dto.getNombre());
        c.setMunicipio(dto.getMunicipio());
        c.setTelefono(dto.getTelefono());
        c.setCorreo(dto.getCorreo());
        c.setFechaActualizacion(LocalDate.now());
        c.setUsuario(usuario);
        return mapper.toDTO(repo.save(c));
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public ComercianteDTO cambiarEstado(Long id, String estado, String usuario) {
        Comerciante c = repo.findById(id).orElseThrow();
        c.setEstado(estado);
        c.setFechaActualizacion(LocalDate.now());
        c.setUsuario(usuario);
        return mapper.toDTO(repo.save(c));
    }

    @Override
    public Resource exportarCSV() {
        List<ComercianteReporteDTO> comerciantes = obtenerComerciantesDesdeFuncion();

        StringBuilder sb = new StringBuilder();
        sb.append("Nombre|Municipio|Teléfono|Correo|Fecha Registro|Estado|Establecimientos|Ingresos|Empleados\n");

        for (ComercianteReporteDTO c : comerciantes) {
            sb.append(c.getNombre()).append("|")
              .append(c.getMunicipio()).append("|")
              .append(c.getTelefono() != null ? c.getTelefono() : "").append("|")
              .append(c.getCorreo() != null ? c.getCorreo() : "").append("|")
              .append(c.getFechaRegistro()).append("|")
              .append(c.getEstado()).append("|")
              .append(c.getCantidadEstablecimientos()).append("|")
              .append(c.getTotalIngresos()).append("|")
              .append(c.getTotalEmpleados()).append("\n");
        }

        byte[] data = sb.toString().getBytes(StandardCharsets.UTF_8);
        return new ByteArrayResource(data);
    }
}
