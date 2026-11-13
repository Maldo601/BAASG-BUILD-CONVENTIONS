# BAASG Build Conventions

## Estado actual

El repositorio centraliza las convenciones de construcción y calidad que compartirán los proyectos Java de BAASG.

### Plugins implementados

| Plugin | Funcionalidad |
|---|---|
| `com.baasg.java-conventions` | Configuración común de Java 25, toolchain y codificación UTF-8. |
| `com.baasg.spring-boot-conventions` | Integración de las convenciones Java con Spring Boot. |
| `com.baasg.testing-conventions` | Configuración de JUnit 6 y JaCoCo para pruebas y cobertura. |
| `com.baasg.code-quality-conventions` | Formato de código mediante Spotless y análisis estático mediante Checkstyle. |

## Roadmap

Mejoras identificadas para futuras iteraciones del repositorio.

| Prioridad | Mejora | Descripción |
|---|---|---|
| Alta | `jacocoTestCoverageVerification` | Establecer umbrales mínimos de cobertura de código y permitir verificar su cumplimiento durante el build. |
| Alta | Verificación de dependencias | Evitar inconsistencias en las versiones de dependencias entre los distintos microservicios. |
| Media | `JavaCompile` warnings | Activar `-Xlint:all` y definir criterios comunes para las advertencias de compilación. |
| Media | Test logging | Unificar la salida y presentación de resultados de los tests. |
| Media | Dependency locking | Mejorar la reproducibilidad de las construcciones mediante el bloqueo de versiones de dependencias. |
| Baja | Convenciones Kotlin | Incorporar convenciones específicas para proyectos Kotlin cuando exista la necesidad de desarrollarlos. |

## Fuera del alcance actual

Las siguientes funcionalidades no se incorporarán por el momento:

- **SonarQube:** corresponde a la infraestructura de análisis y calidad de código, no necesariamente a las convenciones de construcción.
- **Docker:** cada microservicio gestionará su propio proceso de construcción y empaquetado.
- **Publicación automática en Maven:** se decidirá posteriormente el mecanismo de distribución, versionado y consumo de las convenciones.
- **Reglas de arquitectura hexagonal:** se valorará su incorporación cuando existan proyectos consumidores que justifiquen dichas validaciones.

## Criterio de evolución

El objetivo de BAASG-BUILD-CONVENTIONS es proporcionar una configuración común, reutilizable y mantenible para los proyectos de la plataforma.

Las nuevas funcionalidades se incorporarán cuando aporten valor real a los proyectos consumidores, evitando introducir complejidad o abstracciones innecesarias.