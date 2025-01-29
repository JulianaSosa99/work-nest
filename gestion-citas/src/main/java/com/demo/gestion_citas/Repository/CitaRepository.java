package com.demo.gestion_citas.Repository;

import com.demo.gestion_citas.Model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita,Long>
{
    boolean existsByFechaHoraAndFreelancerId(LocalDateTime fechaHora, Long freelancerId);
    List<Cita> findByFreelancerId(Long freelancerId);
    boolean existsByFechaHoraAndFreelancerIdAndIdNot(LocalDateTime fechaHora, Long freelancerId, Long id);

}
