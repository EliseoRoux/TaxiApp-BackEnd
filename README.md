# Central Taxis Java

Este proyecto es una aplicación de gestión para una central de taxis. Permite gestionar la información de los taxis, incluyendo la adición de nuevos taxis y la consulta de los existentes.

## Estructura del Proyecto

El proyecto está organizado de la siguiente manera:

```
central-taxis-java
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── centraltaxis
│   │   │           ├── App.java               # Punto de entrada de la aplicación
│   │   │           ├── controller
│   │   │           │   └── TaxiController.java # Controlador para manejar solicitudes de taxis
│   │   │           ├── model
│   │   │           │   └── Taxi.java           # Modelo que representa un taxi
│   │   │           └── service
│   │   │               └── TaxiService.java     # Lógica de negocio relacionada con taxis
│   │   └── resources
│   │       └── application.properties            # Configuración de la aplicación
│   └── test
│       └── java
│           └── com
│               └── centraltaxis
│                   └── AppTest.java            # Pruebas unitarias para la clase App
├── pom.xml                                      # Configuración del proyecto para Maven
└── README.md                                    # Documentación del proyecto
```

## Requisitos

- Java 11 o superior
- Maven

## Configuración

1. Clona el repositorio en tu máquina local.
2. Navega al directorio del proyecto.
3. Ejecuta `mvn install` para compilar el proyecto y descargar las dependencias necesarias.

## Ejecución

Para ejecutar la aplicación, utiliza el siguiente comando:

```
mvn spring-boot:run
```

## Pruebas

Las pruebas unitarias se encuentran en el directorio `src/test/java/com/centraltaxis`. Para ejecutar las pruebas, utiliza:

```
mvn test
```

## Contribuciones

Las contribuciones son bienvenidas. Si deseas contribuir, por favor abre un issue o envía un pull request.

## Licencia

Este proyecto está bajo la Licencia MIT.