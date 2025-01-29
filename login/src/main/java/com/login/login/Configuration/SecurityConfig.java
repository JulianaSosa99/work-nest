package com.login.login.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF (opcional, solo para desarrollo)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/freelancer/register", "/api/freelancer/login").permitAll() // Permitir login y registro sin autenticación
                        .requestMatchers("/api/freelancer/**").permitAll()// Proteger endpoints de freelancer
                )
                .httpBasic(httpBasic -> httpBasic.disable()); // Deshabilitar autenticación básica
        return http.build();
    }
}
