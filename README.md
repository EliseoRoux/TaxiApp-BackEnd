# API de Gestión de Taxis - Central Taxis

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.18-green)
![Maven](https://img.shields.io/badge/Maven-3.8.5-red)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)

API RESTful robusta y segura construida con Spring Boot para la gestión integral de una central de taxis. Proporciona endpoints para administrar conductores, clientes, reservas y servicios, con un sistema de autenticación basado en JWT.

**Frontend desplegado en Vercel:** 
**Backend desplegado en Render:** 

## ✨ Características Principales

* **Autenticación y Seguridad:** Sistema de login seguro con JSON Web Tokens (JWT). Rutas protegidas y gestión de roles.
* **Gestión de Conductores:** Operaciones CRUD completas para crear, leer, actualizar y eliminar perfiles de conductores.
* **Gestión de Clientes:** Operaciones CRUD para la administración de la base de datos de clientes.
* **Sistema de Reservas:** Lógica de negocio para crear y gestionar reservas, asociándolas a clientes y conductores.
* **Control de Servicios:** Seguimiento de los servicios realizados, con estados y detalles.
* **Manejo de Errores:** Arquitectura centralizada para la gestión de excepciones y respuestas de error consistentes.
* **Configuración por Entornos:** Uso de perfiles de Spring (`prod`/`dev`) para diferenciar configuraciones de base de datos y comportamiento de la aplicación.

## 🚀 Tecnologías Utilizadas

* **Framework:** Spring Boot
* **Seguridad:** Spring Security
* **Acceso a Datos:** Spring Data JPA (Hibernate)
* **Base de Datos:** PostgreSQL
* **Autenticación:** JSON Web Tokens (JWT)
* **Build Tool:** Maven
* **Despliegue:** Docker y Render

## 📋 Prerrequisitos para Ejecutar en Local

* Java JDK 17 o superior.
* Maven 3.8 o superior.
* Una instancia de PostgreSQL en ejecución.

## ⚙️ Instalación y Puesta en Marcha

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/orgs/backend-br/repositories](https://github.com/orgs/backend-br/repositories)
    cd [nombre-de-la-carpeta-backend]
    ```

2.  **Configurar las variables de entorno:**
    Crea un archivo `application.properties` en `src/main/resources` (o modifica el existente) con las credenciales de tu base de datos local y un código secreto para JWT.

3.  **Instalar dependencias y ejecutar:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    La API estará disponible en `http://localhost:8080`.

## ✍️ Autor

* **Eliseo Roux**
* **LinkedIn:** www.linkedin.com/in/eliseo-roux-martínez-a29b89174
