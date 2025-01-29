package com.demo.gestion_citas.Services;

import com.demo.gestion_citas.CitaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQCitaConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQCitaConsumer.class);

  @Autowired
  private EmailService emailService;



    @RabbitListener(queues = "${rabbitmq.queue.json.name}")
    public void consumirCita(CitaDTO mensaje) {
        LOGGER.info("Mensaje recibido desde RabbitMQ: {}", mensaje);

        try {
            switch (mensaje.getAccion().toLowerCase()) {
                case "crear":
                    // Correos para creación de cita
                    emailService.enviarEmail(
                            mensaje.getCorreoCliente(),
                            "Confirmación de tu cita",
                            String.format(
                                    "Hola %s,\n\nTu cita ha sido confirmada.\n\nDescripción: %s\nFecha y Hora: %s\n\nGracias por confiar en nosotros.",
                                    mensaje.getCliente(), mensaje.getDescripcion(), mensaje.getFechaHora()
                            )
                    );
                    emailService.enviarEmail(
                            mensaje.getCorreoFreelancer(),
                            "Nueva Cita Programada",
                            String.format(
                                    "Hola %s,\n\nSe ha registrado una nueva cita:\n\nCliente: %s\nDescripción: %s\nFecha y Hora: %s\n\nPor favor, revisa tu panel.",
                                    mensaje.getNombreFreelancer(), mensaje.getCliente(), mensaje.getDescripcion(), mensaje.getFechaHora()
                            )
                    );
                    break;

                case "actualizar":
                    // Correos para actualización de cita
                    emailService.enviarEmail(
                            mensaje.getCorreoCliente(),
                            "Actualización de tu cita",
                            String.format(
                                    "Hola %s,\n\nTu cita ha sido actualizada con los siguientes detalles:\n\nDescripción: %s\nNueva Fecha y Hora: %s\n\nGracias por confiar en nosotros.",
                                    mensaje.getCliente(), mensaje.getDescripcion(), mensaje.getFechaHora()
                            )
                    );
                    emailService.enviarEmail(
                            mensaje.getCorreoFreelancer(),
                            "Actualización de Cita Programada",
                            String.format(
                                    "Hola %s,\n\nLa cita programada ha sido actualizada:\n\nCliente: %s\nDescripción: %s\nNueva Fecha y Hora: %s\n\nPor favor, revisa tu panel.",
                                    mensaje.getNombreFreelancer(), mensaje.getCliente(), mensaje.getDescripcion(), mensaje.getFechaHora()
                            )
                    );
                    break;

                case "cancelar":
                    // Correos para cancelación de cita
                    emailService.enviarEmail(
                            mensaje.getCorreoCliente(),
                            "Cancelación de tu cita",
                            String.format(
                                    "Hola %s,\n\nLamentamos informarte que tu cita ha sido cancelada.\n\nDescripción: %s\nFecha y Hora: %s\n\nGracias por tu comprensión.",
                                    mensaje.getCliente(), mensaje.getDescripcion(), mensaje.getFechaHora()
                            )
                    );
                    emailService.enviarEmail(
                            mensaje.getCorreoFreelancer(),
                            "Cancelación de una cita programada",
                            String.format(
                                    "Hola %s,\n\nSe ha cancelado una cita programada.\n\nCliente: %s\nDescripción: %s\nFecha y Hora: %s\n\nPor favor, revisa tu panel.",
                                    mensaje.getNombreFreelancer(), mensaje.getCliente(), mensaje.getDescripcion(), mensaje.getFechaHora()
                            )
                    );
                    break;

                default:
                    LOGGER.warn("Acción desconocida: {}", mensaje.getAccion());
            }
        } catch (Exception e) {
            LOGGER.error("Error al enviar correos: {}", e.getMessage());
        }
    }


}

