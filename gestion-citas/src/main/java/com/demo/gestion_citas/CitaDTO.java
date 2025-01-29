package com.demo.gestion_citas;

import java.time.LocalDateTime;

public class CitaDTO {
    private String descripcion;
    private LocalDateTime fechaHora;
    private String cliente;
    private String correoCliente;
    private String correoFreelancer;
    private String nombreFreelancer;
    private String accion; // Nueva propiedad

    // Getters y Setters
    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    // Constructor
    public CitaDTO(String descripcion, LocalDateTime fechaHora, String cliente, String correoCliente, String correoFreelancer, String nombreFreelancer) {
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.correoCliente = correoCliente;
        this.correoFreelancer = correoFreelancer;
        this.nombreFreelancer = nombreFreelancer;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getCorreoFreelancer() {
        return correoFreelancer;
    }

    public void setCorreoFreelancer(String correoFreelancer) {
        this.correoFreelancer = correoFreelancer;
    }

    public String getNombreFreelancer() {
        return nombreFreelancer;
    }

    public void setNombreFreelancer(String nombreFreelancer) {
        this.nombreFreelancer = nombreFreelancer;
    }
}
