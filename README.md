# Proyecto de Gestión de Franquicias

Este proyecto implementa una arquitectura limpia basada en el Scaffold Clean Architecture de Bancolombia. Permite gestionar franquicias, sucursales y productos, utilizando Java con Spring WebFlux y R2DBC para reactividad y PostgreSQL como base de datos.

## 🧱 Estructura del Proyecto

```
├── app-service                       # Módulo de entrada (Spring Boot App)
│   └── MainApplication.java
├── domain                           # Lógica de negocio (modelo y uso de casos)
│   ├── model                        # Entidades del dominio
│   └── usecase                      # Casos de uso
├── infrastructure
│   ├── entry-points                 # Entradas (API REST, EventHandler, etc.)
│   │   └── api                      # Controladores REST
│   └── driven-adapters              # Adaptadores externos (DB, colas, etc.)
│       └── r2dbc-postgresql         # Implementación R2DBC con PostgreSQL
├── build.gradle                     # Build script
└── settings.gradle
```

## 🚀 Ejecutar localmente

### Pre-requisitos

- Java 17
- Gradle 8+
- Docker (para PostgreSQL local si no usas RDS)
- PostgreSQL o una instancia RDS

### 🛠️ Variables de entorno necesarias

Agregar valores al archivo `.yml` para configurar variables del sistema:

```bash
adapters:
  r2dbc:
    host: localhost
    port: 5432
    database: frenchise_db
    schema: public
    username: postgres
    password: 123456
```

### 🛠️ Tablas de la base de datos
Squema SQL con las tablas en los recursos de app-service



### 🔧 Compilar y ejecutar

```bash
./gradlew clean build
./gradlew :app-service:bootRun
```

O desde tu IDE (IntelliJ, VSCode) ejecuta `MainApplication.java` del módulo `app-service`.


## 📦 Swagger UI
Puedes acceder a la documentación de la API en Swagger UI en `http://localhost:8080/webjars/swagger-ui/index.html#/`.


## Postman 
Puedes importar el archivo `frenchise.postman_collection.json` en Postman para probar las APIs.

## Terraform para Infraestructura 

Puedes subirlo a ECR y desplegarlo con ECS como se muestra en la infraestructura Terraform.

---

## ✨ Créditos

Arquitectura basada en el [Scaffold Clean Architecture](https://github.com/bancolombia/scaffold-clean-architecture) de Bancolombia.