**Work Nest - Plataforma de Gestión para Freelancers**
**Autores:** Priscila Zúñiga, Matias Robayo, Juliana Sosa
**Fecha:** Enero 2025
**Tecnologías: **React.js, Spring Boot, MongoDB, PostgreSQL, Azure API Gateway, RabbitMQ

:tw-1f1e9-1f1ea: :smile:**Descripción del Proyecto**
Work Nest es una plataforma diseñada para gestionar citas, planificar tareas y estimar costos para freelancers de manera eficiente. La aplicación permite a los usuarios organizar su agenda, llevar un registro de sus proyectos y clientes, y calcular los costos de sus servicios de manera automatizada.

El sistema está compuesto por varios microservicios que se comunican a través de** Azure Api Management**, asegurando una estructura escalable y segura.
**
Objetivos del Proyecto**
- Facilitar la gestión de citas con clientes.
- Permitir la planificación y seguimiento de tareas de trabajo.
- Automatizar el cálculo de costos de proyectos.
- Garantizar la seguridad con autenticación basada en JWT.
- Proveer una API centralizada a través de Azure API Gateway.
- Arquitectura del Proyecto
- El sistema sigue una arquitectura basada en microservicios, con un API Gateway en Azure que centraliza el acceso a cada módulo.

**Componentes Principales:**

1. **Frontend (React.js)**

- Interfaz moderna y responsiva para freelancers.
- Manejo de autenticación con JWT.
- Consumo de APIs mediante Azure API Gateway.

2. **Backend (Spring Boot - Microservicios)**

- Microservicio de Autenticación: Manejo de usuarios y autenticación con JWT.
- Microservicio de Gestión de Citas: CRUD de citas con notificaciones por correo (RabbitMQ).
- Microservicio de Planificación de Tareas: Gestión de tareas en MongoDB.
- Microservicio de Estimación de Costos: Cálculo automático de costos de proyectos.

3. **Infraestructura:**

- Base de Datos: PostgreSQL (Citas) & MongoDB (Tareas y Estimaciones).
- Mensajería Asíncrona: RabbitMQ (Notificaciones).
- Azure API Management: Centraliza y protege los endpoints.
