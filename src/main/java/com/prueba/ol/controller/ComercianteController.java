package com.prueba.ol.controller;

import com.prueba.ol.DTO.ComercianteDTO;
import com.prueba.ol.service.ComercianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comerciantes")
@Tag(name = "CrudComerciantes", description = "Endpoints para comerciantes")
public class ComercianteController {

    private final ComercianteService service;

    public ComercianteController(ComercianteService service) {
        this.service = service;
    }

    @Operation(summary = "Listar comerciantes", description = "Lista comerciantes con filtros por nombre, estado y fecha. Paginado (5 por defecto).")
    @GetMapping
    public Page<ComercianteDTO> listar(@RequestParam(required = false) String nombre,
                                       @RequestParam(required = false) String estado,
                                       @RequestParam(required = false) String fecha,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.listar(nombre, estado, fecha, pageable);
    }

    @Operation(summary = "Buscar comerciante por ID", description = "Devuelve los datos del comerciante con el ID especificado.")
    @GetMapping("/{id}")
    public ResponseEntity<ComercianteDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear comerciante", description = "Crea un nuevo comerciante con datos básicos y los guarda con auditoría del usuario actual.")
    @PostMapping
    public ComercianteDTO crear(@Valid @RequestBody ComercianteDTO dto, Authentication auth) {
        return service.crear(dto, auth.getName());
    }

    @Operation(summary = "Actualizar comerciante", description = "Actualiza los datos del comerciante y actualiza campos de auditoría automáticamente.")
    @PutMapping("/{id}")
    public ComercianteDTO actualizar(@PathVariable Long id, @RequestBody ComercianteDTO dto, Authentication auth) {
        return service.actualizar(id, dto, auth.getName());
    }

    @Operation(summary = "Eliminar comerciante", description = "Elimina un comerciante según su ID. Solo permitido para usuarios con rol ADMINISTRADOR.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @Operation(summary = "Cambiar estado del comerciante", description = "Modifica el estado del comerciante a Activo o Inactivo con PATCH.")
    @PatchMapping("/{id}/estado")
    public ComercianteDTO cambiarEstado(@PathVariable Long id,
                                        @RequestParam String estado,
                                        Authentication auth) {
        return service.cambiarEstado(id, estado, auth.getName());
    }

    
    @Operation(summary = "Exportar comerciantes activos a CSV", description = "Genera y descarga archivo .csv con datos agregados. Requiere rol ADMINISTRADOR.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/exportar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Resource> exportarCSV() {
        Resource file = service.exportarCSV();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comerciantes.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }
}
