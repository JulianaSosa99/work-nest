package com.planificador.tareas.planificador.repository;

import com.planificador.tareas.planificador.model.Tarea;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TareaRepository extends MongoRepository<Tarea, String> {

    // Encuentra todas las tareas asociadas a un usuario específico
    List<Tarea> findByUsuarioId(String usuarioId);

    // Encuentra una tarea por ID y usuarioId para validar autorización
    Optional<Tarea> findByIdAndUsuarioId(String id, String usuarioId);
}
