package com.planificador.tareas.planificador.controller;

import com.planificador.tareas.planificador.model.Tarea;
import com.planificador.tareas.planificador.service.TareaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public List<Tarea> listarTareas(@RequestAttribute("userId") String usuarioId) {
        return tareaService.listarTareasPorUsuario(usuarioId);
    }

    @PostMapping
    public Tarea guardarTarea(@RequestAttribute("userId") String usuarioId, @RequestBody Tarea tarea) {
        return tareaService.guardarTarea(usuarioId, tarea);
    }

    @PutMapping("/{id}")
    public Tarea actualizarTarea(@RequestAttribute("userId") String usuarioId,
                                 @PathVariable String id,
                                 @RequestBody Tarea tarea) {
        return tareaService.actualizarTarea(id, usuarioId, tarea);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@RequestAttribute("userId") String usuarioId, @PathVariable String id) {
        tareaService.eliminarTarea(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
