package com.demo.gestion_citas.Services;

import com.demo.gestion_citas.CitaDTO;
import com.demo.gestion_citas.Model.Cita;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQCitaProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQCitaProducer.class);

    private final RabbitTemplate rabbitTemplate;
    public RabbitMQCitaProducer(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate=rabbitTemplate;
    }
   /* public void enviarCita(Cita cita)
    {
        LOGGER.info("Enviando mensaje de cita a RabbitMQ: {}", cita);
        rabbitTemplate.convertAndSend(exchange,routingJsonKey,cita);
    }
    */

    public void enviarCita(Cita cita, String correoFreelancer, String nombreFreelancer, String accion) {
        if (cita.getEmailCliente() == null || correoFreelancer == null || nombreFreelancer == null) {
            LOGGER.error("Datos incompletos: falta el correo del cliente o del freelancer.");
            throw new RuntimeException("Datos incompletos: falta el correo del cliente o del freelancer.");
        }

        CitaDTO mensaje = new CitaDTO(
                cita.getDescripcion(),
                cita.getFechaHora(),
                cita.getCliente(),
                cita.getEmailCliente(),
                correoFreelancer,
                nombreFreelancer
        );
        mensaje.setAccion(accion); // Nueva propiedad para definir la acción

        rabbitTemplate.convertAndSend(exchange, routingJsonKey, mensaje);
        LOGGER.info("Mensaje enviado a RabbitMQ: {}", mensaje);
    }





}
