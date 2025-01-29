package com.estimacion_costos.estimacion_costos.repository;

import com.estimacion_costos.estimacion_costos.model.SistemaEstimaciones;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SistemaEstimacionesRepository extends MongoRepository<SistemaEstimaciones, String> {
    List<SistemaEstimaciones> findByUsuarioId(String usuarioId);
    Optional<SistemaEstimaciones> findByIdAndUsuarioId(String id, String usuarioId);


}
