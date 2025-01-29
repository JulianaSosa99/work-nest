package com.estimacion_costos.estimacion_costos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estimaciones")
public class SistemaEstimaciones {

    @Id
    private String id;
    private String usuarioId;
    private String nombreProyecto;
    private String nombreEmpresa;
    private Double valorPorHora;
    private Integer cantidadHoras;
    private Integer cantidadDias; // Nuevo campo para días
    private Double costoTotal; // Calculado automáticamente
    private String descripcion;

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Double getValorPorHora() {
        return valorPorHora;
    }

    public void setValorPorHora(Double valorPorHora) {
        this.valorPorHora = valorPorHora;
    }

    public Integer getCantidadHoras() {
        return cantidadHoras;
    }

    public void setCantidadHoras(Integer cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
    }

    public Integer getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(Integer cantidadDias) {
        this.cantidadDias = cantidadDias;
    }

    public Double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public SistemaEstimaciones(String id, String usuarioId, String nombreProyecto, String nombreEmpresa, Double valorPorHora, Integer cantidadHoras, Integer cantidadDias, Double costoTotal, String descripcion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nombreProyecto = nombreProyecto;
        this.nombreEmpresa = nombreEmpresa;
        this.valorPorHora = valorPorHora;
        this.cantidadHoras = cantidadHoras;
        this.cantidadDias = cantidadDias;
        this.costoTotal = costoTotal;
        this.descripcion = descripcion;
    }
}
