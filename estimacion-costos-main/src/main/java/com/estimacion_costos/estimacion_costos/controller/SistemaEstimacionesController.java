package com.estimacion_costos.estimacion_costos.controller;

import com.estimacion_costos.estimacion_costos.model.SistemaEstimaciones;
import com.estimacion_costos.estimacion_costos.service.SistemaEstimacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estimaciones")
public class SistemaEstimacionesController {

    @Autowired
    private SistemaEstimacionesService estimacionesService;

    @PostMapping
    public SistemaEstimaciones guardarEstimacion(@RequestAttribute("userId") String usuarioId, @RequestBody SistemaEstimaciones estimacion) {
        return estimacionesService.guardarEstimacion(usuarioId, estimacion);
    }

    @GetMapping
    public List<SistemaEstimaciones> listarEstimaciones(@RequestAttribute("userId") String usuarioId) {
        return estimacionesService.listarEstimaciones(usuarioId);
    }

    @PutMapping("/{id}")
    public SistemaEstimaciones actualizarEstimacion(@RequestAttribute("userId") String usuarioId, @PathVariable String id, @RequestBody SistemaEstimaciones detallesEstimacion) {
        return estimacionesService.actualizarEstimacion(usuarioId, id, detallesEstimacion);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstimacion(@RequestAttribute("userId") String usuarioId, @PathVariable String id) {
        estimacionesService.eliminarEstimacion(usuarioId, id);
        return ResponseEntity.noContent().build();
    }

}
