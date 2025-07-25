package com.prueba.ol.repository;
import jakarta.persistence.*;
import java.math.BigDecimal;

import com.prueba.ol.entity.Comerciante;

@Entity
@Table(name = "establecimiento")
public class Establecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private BigDecimal ingresos;

    @Column(name = "numero_empleados")
    private Integer numeroEmpleados;

    @ManyToOne
    @JoinColumn(name = "comerciante_id", nullable = false)
    private Comerciante comerciante;

    @Column(name = "fecha_actualizacion")
    private java.time.LocalDate fechaActualizacion;

    private String usuario;

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getIngresos() { return ingresos; }
    public void setIngresos(BigDecimal ingresos) { this.ingresos = ingresos; }

    public Integer getNumeroEmpleados() { return numeroEmpleados; }
    public void setNumeroEmpleados(Integer numeroEmpleados) { this.numeroEmpleados = numeroEmpleados; }

    public Comerciante getComerciante() { return comerciante; }
    public void setComerciante(Comerciante comerciante) { this.comerciante = comerciante; }

    public java.time.LocalDate getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(java.time.LocalDate fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
}