package com.estimacion_costos.estimacion_costos.service;

import com.estimacion_costos.estimacion_costos.DTO.FreelancerDTO;
import com.estimacion_costos.estimacion_costos.model.SistemaEstimaciones;
import com.estimacion_costos.estimacion_costos.repository.SistemaEstimacionesRepository;
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
public class SistemaEstimacionesService {

    @Autowired
    private SistemaEstimacionesRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${login.api.url}")
    private String freelancerApiUrl;

    public List<SistemaEstimaciones> listarEstimaciones(String usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public SistemaEstimaciones guardarEstimacion(String usuarioId, SistemaEstimaciones estimacion) {
        validarFreelancer(usuarioId); // Validar el freelancer antes de guardar
        validarHorasYDias(estimacion);
        estimacion.setUsuarioId(usuarioId);
        estimacion.setCostoTotal(estimacion.getValorPorHora() * estimacion.getCantidadHoras());
        return repository.save(estimacion);
    }

    public SistemaEstimaciones actualizarEstimacion(String usuarioId, String id, SistemaEstimaciones detallesEstimacion) {
        validarFreelancer(usuarioId); // Validar el freelancer antes de actualizar
        validarHorasYDias(detallesEstimacion);

        SistemaEstimaciones estimacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estimación no encontrada."));

        if (!estimacion.getUsuarioId().equals(usuarioId)) {
            throw new RuntimeException("No autorizado para modificar esta estimación.");
        }

        estimacion.setNombreProyecto(detallesEstimacion.getNombreProyecto());
        estimacion.setNombreEmpresa(detallesEstimacion.getNombreEmpresa());
        estimacion.setValorPorHora(detallesEstimacion.getValorPorHora());
        estimacion.setCantidadHoras(detallesEstimacion.getCantidadHoras());
        estimacion.setCantidadDias(detallesEstimacion.getCantidadDias());
        estimacion.setDescripcion(detallesEstimacion.getDescripcion());
        estimacion.setCostoTotal(detallesEstimacion.getValorPorHora() * detallesEstimacion.getCantidadHoras());

        return repository.save(estimacion);
    }

    private void validarFreelancer(String usuarioId) {
        String url = freelancerApiUrl + usuarioId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + usuarioId);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<FreelancerDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, FreelancerDTO.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("El freelancer con ID " + usuarioId + " no existe o no está autorizado.");
        }
    }

    private void validarHorasYDias(SistemaEstimaciones estimacion) {
        int maxHorasPorDia = 24;
        if (estimacion.getCantidadDias() == null || estimacion.getCantidadDias() <= 0) {
            throw new IllegalArgumentException("Los días deben ser mayor que 0.");
        }
        if (estimacion.getCantidadHoras() > estimacion.getCantidadDias() * maxHorasPorDia) {
            throw new IllegalArgumentException("La cantidad de horas supera las horas disponibles según los días.");
        }
    }
    public void eliminarEstimacion(String usuarioId, String id) {
        SistemaEstimaciones estimacion = repository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Estimación no encontrada o no autorizada."));
        repository.delete(estimacion);
    }

}
