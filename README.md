# BAASG-BUILD-CONVENTIONS

Repositorio centralizado de plugins y convenciones de construcción para el ecosistema **BAASG**.

Su objetivo es establecer una configuración homogénea entre los proyectos Java del ecosistema, evitando duplicidades y facilitando el mantenimiento, la evolución y la escalabilidad de sus sistemas de construcción.

## Objetivos

- Centralizar las convenciones de construcción de los proyectos BAASG.
- Unificar las versiones y configuraciones de las herramientas de desarrollo.
- Reducir la duplicación de configuración entre repositorios.
- Estandarizar la compilación, las pruebas, la cobertura y la calidad del código.
- Facilitar la evolución de las convenciones de forma independiente a los microservicios.

## Stack tecnológico

| Tecnología | Versión |
|---|---:|
| Java | 25 |
| Gradle | 9.9.1 |
| Kotlin DSL | 2.4.21 |
| Spring Boot | 4.1.2 |
| JUnit | 6.0.1 |
| JaCoCo | 0.8.16 |
| Spotless | 8.11.0 |
| Google Java Format | 1.27.0 |
| Checkstyle | 14.4.0 |

Las versiones de dependencias y plugins se centralizan, cuando corresponde, en el catálogo `gradle/libs.versions.toml`.

## Plugins disponibles

El repositorio proporciona plugins independientes que los proyectos consumidores pueden aplicar según sus necesidades.

### 1. Java Conventions

**Plugin ID:** `com.baasg.java-conventions`

Establece las convenciones comunes para proyectos Java.

Responsabilidades:

- Aplicación del plugin Java de Gradle.
- Configuración de Java Toolchain 25.
- Codificación UTF-8.
- Configuración del nivel de compatibilidad de compilación Java 25.

### 2. Spring Boot Conventions

**Plugin ID:** `com.baasg.spring-boot-conventions`

Establece las convenciones para los servicios basados en Spring Boot.

Responsabilidades:

- Aplicación automática de Java Conventions.
- Aplicación del plugin Spring Boot.
- Integración con las dependencias y convenciones de Spring Boot.

### 3. Testing Conventions

**Plugin ID:** `com.baasg.testing-conventions`

Centraliza la configuración de pruebas y cobertura de código.

Responsabilidades:

- Configuración de JUnit Jupiter.
- Ejecución de pruebas mediante JUnit Platform.
- Integración de JaCoCo.
- Generación automática de informes de cobertura XML y HTML.
- Ejecución del informe de cobertura después de las pruebas.

### 4. Code Quality Conventions

**Plugin ID:** `com.baasg.code-quality-conventions`

Centraliza las herramientas de formato y análisis estático del código.

Responsabilidades:

- Integración de Spotless.
- Formateo Java mediante Google Java Format.
- Eliminación de imports no utilizados.
- Normalización de espacios en blanco y saltos de línea.
- Integración de Checkstyle.
- Configuración Checkstyle incluida como recurso del propio plugin.
- Generación de informes XML y HTML.

## Arquitectura del repositorio

Los plugins se implementan mediante plugins binarios de Gradle utilizando Kotlin.

Estructura:

```text
BAASG-BUILD-CONVENTIONS/
│
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/
│   │   │       └── baasg/
│   │   │           └── gradle/
│   │   │               ├── JavaConventionsPlugin.kt
│   │   │               ├── SpringBootConventionsPlugin.kt
│   │   │               ├── TestingConventionsPlugin.kt
│   │   │               └── CodeQualityConventionsPlugin.kt
│   │   │
│   │   └── resources/
│   │       └── com/
│   │           └── baasg/
│   │               └── gradle/
│   │                   └── checkstyle.xml
│   │
│   └── test/
│       └── kotlin/
│           └── com/
│               └── baasg/
│                   └── gradle/
│                       ├── JavaConventionsPluginTest.kt
│                       ├── SpringBootConventionsPluginTest.kt
│                       ├── TestingConventionsPluginTest.kt
│                       └── CodeQualityConventionsPluginTest.kt
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## Desarrollo

### Requisitos

- Java JDK 25.
- Gradle Wrapper incluido en el repositorio.
- Acceso a los repositorios de dependencias configurados.

No es necesario instalar Gradle globalmente.

### Compilación y pruebas

Ejecutar todas las pruebas:

```bash
./gradlew test
```

Ejecutar una compilación limpia y todas las pruebas:

```bash
./gradlew clean test
```

Compilar el proyecto:

```bash
./gradlew build
```

Consultar las tareas disponibles:

```bash
./gradlew tasks
```
Testing Individual de Tareas: 

```bash
./gradlew test --tests "com.baasg.gradle.CodeQualityConventionsPluginTest" --info --stacktrace
```

## Filosofía de diseño

BAASG-BUILD-CONVENTIONS sigue estos principios:

- **Centralización:** las convenciones comunes se mantienen en un único repositorio.
- **Reutilización:** los proyectos consumidores aplican plugins en lugar de replicar configuraciones.
- **Modularidad:** cada plugin tiene una responsabilidad concreta.
- **Independencia:** los proyectos pueden adoptar únicamente las convenciones que necesitan.
- **Mantenibilidad:** las modificaciones de las convenciones se realizan de forma centralizada.
- **Evolución controlada:** las nuevas convenciones se incorporan cuando existe una necesidad real.

## Integración en BAASG

Este repositorio proporciona la base de construcción para los proyectos Java del ecosistema BAASG.

Los microservicios podrán adoptar estas convenciones sin tener que mantener individualmente la configuración de compilación, pruebas y calidad del código.

La evolución de las convenciones se realizará de forma independiente a la lógica de negocio de cada servicio.

---

**BAASG · Build Conventions**  
*Consistencia, reutilización y mantenibilidad en el ecosistema BAASG.*


## Roadmap

Consulta las funcionalidades y mejoras previstas para futuras versiones en [Future Features](FutureFeatures.md).
