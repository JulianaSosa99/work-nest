package com.login.login.Repository;

import com.login.login.Models.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreelancerRepository extends JpaRepository<Freelancer,Long> {
    boolean existsByCorreo(String correo); // Aquí "existsByCorreo" es el nombre correcto
    Optional<Freelancer> findByCorreo(String correo);
}