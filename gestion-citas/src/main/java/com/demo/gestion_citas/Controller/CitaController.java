package com.demo.gestion_citas.Controller;

import com.demo.gestion_citas.Model.Cita;
import com.demo.gestion_citas.Repository.CitaRepository;
import com.demo.gestion_citas.Services.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@Tag(name="Cita Controller")
public class CitaController {
    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaRepository citaRepository;

    @Operation(summary = "Obtener todas las citas pendientes de un freelancer")
    @GetMapping("/freelancer/citas")
    public ResponseEntity<List<Cita>> obtenerCitasFreelancer(@RequestAttribute("userId") Long userId) {
        List<Cita> citas = citaService.obtenerCitasPorFreelancer(userId);
        return ResponseEntity.ok(citas);
    }

    @Operation(summary = "Crear una nueva cita en la base de datos")
    @PostMapping("/freelancer/citas")
    public ResponseEntity<?> crearCita(@RequestAttribute("userId") Long userId,
                                       @RequestHeader("Authorization") String authHeader,
                                       @RequestBody Cita cita) {
        String token = authHeader.replace("Bearer ", "");
        cita.setFreelancerId(userId);
        Cita nuevaCita = citaService.crearCita(cita, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }

    @Operation(summary = "Actualizar una cita existente en la base de datos")
    @PutMapping("/freelancer/citas/{id}")
    public ResponseEntity<?> actualizarCita(@PathVariable Long id,
                                            @RequestAttribute("userId") Long userId,
                                            @RequestHeader("Authorization") String authHeader,
                                            @Valid @RequestBody Cita cita) {
        String token = authHeader.replace("Bearer ", "");

        // Configurar el freelancerId automáticamente en la cita
        cita.setFreelancerId(userId);

        // Llamar al servicio para actualizar
        Cita citaActualizada = citaService.actualizarCita(id, cita, token);

        return ResponseEntity.ok(citaActualizada);
    }


    @Operation(summary = "Eliminar una cita de la base de datos")
    @DeleteMapping("/delete/{id}")
    public void eliminarCita(@PathVariable Long id,
                             @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        citaService.eliminarCita(id, token);
    }
}
