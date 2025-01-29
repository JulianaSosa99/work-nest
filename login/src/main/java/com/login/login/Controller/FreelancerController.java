package com.login.login.Controller;

import com.login.login.Repository.FreelancerRepository;
import com.login.login.Models.Freelancer;
import com.login.login.Service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController

@RequestMapping("/api/freelancer")

public class FreelancerController {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    @GetMapping("/{freelancerId}")
    public ResponseEntity<?> obtenerFreelancer(@PathVariable Long freelancerId) {
        Freelancer freelancer = freelancerRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer no encontrado"));
        return ResponseEntity.ok(freelancer);
    }


    @PostMapping("/register")
    public ResponseEntity<?> registrarFreelancer(@RequestBody Freelancer freelancer) {
        // Verificar si el correo ya está registrado
        if (freelancerRepository.existsByCorreo(freelancer.getCorreo())) {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }

        // Codificar la contraseña antes de guardarla
        freelancer.setContrasena(passwordEncoder.encode(freelancer.getContrasena()));
        Freelancer nuevoFreelancer = freelancerRepository.save(freelancer);

        // Generar JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("nombre", nuevoFreelancer.getNombre());
        claims.put("correo", nuevoFreelancer.getCorreo());

        String token = jwtService.generateToken(claims, String.valueOf(nuevoFreelancer.getId()));

        // Respuesta con el token
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", String.valueOf(nuevoFreelancer.getId()));

        return ResponseEntity.status(201).body(response);
    }



    @PostMapping("/login")
    public ResponseEntity<?> autenticarFreelancer(@RequestBody Freelancer freelancer) {
        Freelancer encontrado = freelancerRepository.findByCorreo(freelancer.getCorreo())
                .orElseThrow(() -> new RuntimeException("El correo no está registrado."));

        if (!passwordEncoder.matches(freelancer.getContrasena(), encontrado.getContrasena())) {
            return ResponseEntity.badRequest().body("Contraseña incorrecta.");
        }

        // Genera el token JWT
        String token = jwtService.generateToken(Map.of("nombre", encontrado.getNombre(), "correo", encontrado.getCorreo()), String.valueOf(encontrado.getId()));
        return ResponseEntity.ok(Map.of("token", token, "userId", encontrado.getId()));
    }
    }


