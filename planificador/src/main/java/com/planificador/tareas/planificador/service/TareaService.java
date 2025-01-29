package com.planificador.tareas.planificador.service;

import com.planificador.tareas.planificador.DTOS.FreelancerDTO;
import com.planificador.tareas.planificador.model.Tarea;
import com.planificador.tareas.planificador.repository.TareaRepository;
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
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Value("${login.api.url}")
    private String freelancerApiUrl;
    public List<Tarea> listarTareasPorUsuario(String usuarioId) {
        return tareaRepository.findByUsuarioId(usuarioId);
    }

    public Tarea guardarTarea(String usuarioId, Tarea tarea) {
        tarea.setUsuarioId(usuarioId);
        return tareaRepository.save(tarea);
    }

    public Tarea actualizarTarea(String id, String usuarioId, Tarea tareaDetalles) {
        Tarea tarea = tareaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada o no autorizada."));

        tarea.setNombre(tareaDetalles.getNombre());
        tarea.setDescripcion(tareaDetalles.getDescripcion());
        tarea.setFechaInicio(tareaDetalles.getFechaInicio());
        tarea.setFechaFin(tareaDetalles.getFechaFin());
        tarea.setEstado(tareaDetalles.getEstado());

        return tareaRepository.save(tarea);
    }

    public void eliminarTarea(String id, String usuarioId) {
        Tarea tarea = tareaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada o no autorizada."));

        tareaRepository.delete(tarea);
    }

    private FreelancerDTO validarFreelancer(String usuarioId, String token) {
        String url = freelancerApiUrl + usuarioId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<FreelancerDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, FreelancerDTO.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("El freelancer no existe.");
        }

        return response.getBody();
    }
}
