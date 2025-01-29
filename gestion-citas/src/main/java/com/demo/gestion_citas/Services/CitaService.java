package com.demo.gestion_citas.Services;

import com.demo.gestion_citas.FreelancerDTO;
import com.demo.gestion_citas.Model.Cita;
import com.demo.gestion_citas.Repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CitaService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CitaRepository citaRepository;

    @Value("${login.api.url}")
    private String freelancerApiUrl;
    @Autowired
    private RabbitMQCitaProducer rabbitMQCitaProducer;

    @Autowired
    private EmailService emailService;

    /**
     * Obtiene las citas de un freelancer por su ID.
     *
     * @param freelancerId ID del freelancer
     * @return Lista de citas asociadas al freelancer
     */
    public List<Cita> obtenerCitasPorFreelancer(Long freelancerId) {
        return citaRepository.findByFreelancerId(freelancerId);
    }

    /**
     * Crea una nueva cita.
     *
     * @param cita  Objeto de cita con datos
     * @param token Token JWT del freelancer autenticado
     * @return Objeto de cita creado
     */
    public Cita crearCita(Cita cita, String token) {
        System.out.println("Token recibido: " + token);

        if (citaRepository.existsByFechaHoraAndFreelancerId(cita.getFechaHora(), cita.getFreelancerId())) {
            throw new RuntimeException("Ya existe una cita para este freelancer en esta fecha y hora.");
        }

        FreelancerDTO freelancerDTO = obtenerFreelancer(cita.getFreelancerId(), token);
        if (freelancerDTO == null) {
            throw new RuntimeException("El freelancer no existe.");
        }

        if (cita.getEmailCliente() == null || cita.getEmailCliente().isEmpty()) {
            throw new RuntimeException("El correo del cliente es obligatorio.");
        }

        Cita nuevaCita = citaRepository.save(cita);
        rabbitMQCitaProducer.enviarCita(nuevaCita, freelancerDTO.getCorreo(), freelancerDTO.getNombre(), "crear");
        return nuevaCita;
    }


    /**
     * Actualiza una cita existente.
     *
     * @param id           ID de la cita a actualizar
     * @param citaDetalles Detalles nuevos de la cita
     * @param token        Token JWT del freelancer autenticado
     * @return Objeto de cita actualizado
     */
    public Cita actualizarCita(Long id, Cita citaDetalles, String token) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        // Validar que la cita pertenece al freelancer autenticado
        if (!cita.getFreelancerId().equals(citaDetalles.getFreelancerId())) {
            throw new RuntimeException("No puedes actualizar citas de otro freelancer.");
        }

        // Validar conflictos de horario
        /*if (citaRepository.existsByFechaHoraAndFreelancerId(citaDetalles.getFechaHora(), citaDetalles.getFreelancerId())) {
            throw new RuntimeException("Ya existe una cita para este freelancer en esta fecha y hora.");
        }*/

        // Actualizar los datos de la cita
        cita.setDescripcion(citaDetalles.getDescripcion());
        cita.setFechaHora(citaDetalles.getFechaHora());
        cita.setCliente(citaDetalles.getCliente());
        cita.setEmailCliente(citaDetalles.getEmailCliente());

        FreelancerDTO freelancerDTO = obtenerFreelancer(citaDetalles.getFreelancerId(), token);

        Cita citaActualizada = citaRepository.save(cita);

        // Notificar a través de RabbitMQ
        rabbitMQCitaProducer.enviarCita(citaActualizada, freelancerDTO.getCorreo(), freelancerDTO.getNombre(), "actualizar");

        return citaActualizada;
    }

    /**
     * Elimina una cita por su ID.
     *
     * @param id    ID de la cita a eliminar
     * @param token Token JWT del freelancer autenticado
     */
    public void eliminarCita(Long id, String token) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        FreelancerDTO freelancerDTO = obtenerFreelancer(cita.getFreelancerId(), token);

        citaRepository.deleteById(id);
        rabbitMQCitaProducer.enviarCita(cita, freelancerDTO.getCorreo(), freelancerDTO.getNombre(), "cancelar");
    }

    /**
     * Obtiene los datos del freelancer desde el sistema de login.
     *
     * @param freelancerId ID del freelancer
     * @param token        Token JWT del freelancer autenticado
     * @return Objeto FreelancerDTO con los datos del freelancer
     */
    private FreelancerDTO obtenerFreelancer(Long freelancerId, String token) {
        if (freelancerId == null) {
            return null;
        }

        try {
            String url = freelancerApiUrl + freelancerId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<FreelancerDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, FreelancerDTO.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("El freelancer con ID " + freelancerId + " no existe.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener información del freelancer: " + e.getMessage());
        }
    }
}
