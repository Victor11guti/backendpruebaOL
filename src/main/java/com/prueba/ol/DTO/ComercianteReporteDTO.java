package com.prueba.ol.DTO;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ComercianteReporteDTO {
	    private String nombre;
	    private String municipio;
	    private String telefono;
	    private String correo;
	    private LocalDate fechaRegistro;
	    private String estado;
	    private int cantidadEstablecimientos;
	    private BigDecimal totalIngresos;
	    private int totalEmpleados;
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getMunicipio() {
			return municipio;
		}
		public void setMunicipio(String municipio) {
			this.municipio = municipio;
		}
		public String getTelefono() {
			return telefono;
		}
		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}
		public String getCorreo() {
			return correo;
		}
		public void setCorreo(String correo) {
			this.correo = correo;
		}
		public LocalDate getFechaRegistro() {
			return fechaRegistro;
		}
		public void setFechaRegistro(LocalDate fechaRegistro) {
			this.fechaRegistro = fechaRegistro;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public int getCantidadEstablecimientos() {
			return cantidadEstablecimientos;
		}
		public void setCantidadEstablecimientos(int cantidadEstablecimientos) {
			this.cantidadEstablecimientos = cantidadEstablecimientos;
		}
		public BigDecimal getTotalIngresos() {
			return totalIngresos;
		}
		public void setTotalIngresos(BigDecimal totalIngresos) {
			this.totalIngresos = totalIngresos;
		}
		public int getTotalEmpleados() {
			return totalEmpleados;
		}
		public void setTotalEmpleados(int totalEmpleados) {
			this.totalEmpleados = totalEmpleados;
		}
	    
	    
	    
}
