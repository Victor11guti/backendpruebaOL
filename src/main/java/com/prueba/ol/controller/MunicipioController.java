package com.prueba.ol.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.ol.service.MunicipioService;
import com.prueba.ol.util.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/municipios")
@Tag(name = "Municipios", description = "Endpoints para municipios")
public class MunicipioController {

    private final MunicipioService municipioService;

    public MunicipioController(MunicipioService municipioService) {
        this.municipioService = municipioService;
    }

    @GetMapping
    public ApiResponse<List<String>> getMunicipios() {
        List<String> municipios = municipioService.obtenerMunicipios();
        return new ApiResponse<>(true, "Municipios cargados correctamente", municipios);
    }
}
