package com.prueba.ol.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prueba.ol.repository.ComercianteRepository;
import com.prueba.ol.service.MunicipioService;
import org.springframework.cache.annotation.Cacheable;


@Service
public class MunicipioServiceImpl implements MunicipioService {

    private final ComercianteRepository comercianteRepo;

    public MunicipioServiceImpl(ComercianteRepository comercianteRepo) {
        this.comercianteRepo = comercianteRepo;
    }

    @Override
    @Cacheable("municipios") // Cache en memoria
    public List<String> obtenerMunicipios() {
        return comercianteRepo.obtenerMunicipiosUnicos();
    }
}