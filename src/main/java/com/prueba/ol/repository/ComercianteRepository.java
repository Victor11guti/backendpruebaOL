package com.prueba.ol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.prueba.ol.entity.Comerciante;

import java.util.List;

public interface ComercianteRepository extends JpaRepository<Comerciante, Long>,JpaSpecificationExecutor<Comerciante>  {

    @Query("SELECT DISTINCT c.municipio FROM Comerciante c")
    List<String> obtenerMunicipiosUnicos();
}