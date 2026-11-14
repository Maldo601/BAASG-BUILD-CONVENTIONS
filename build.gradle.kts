
plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "com.baasg.build"
version = "0.0.1"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    // Plugin dependencies
    implementation(libs.spring.boot.gradle.plugin)
    implementation(libs.spotless.gradle.plugin)

    // Plugin testing dependencies
    testImplementation(gradleTestKit())
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

gradlePlugin {
    plugins {
        register("javaConventions") {
            id = "com.baasg.java-conventions"
            implementationClass = "com.baasg.gradle.JavaConventionsPlugin"
            displayName = "BAASG Java Conventions"
            description = "Common Java conventions for BAASG projects"
        }

        register("springBootConventions") {
            id = "com.baasg.spring-boot-conventions"
            implementationClass = "com.baasg.gradle.SpringBootConventionsPlugin"
            displayName = "BAASG Spring Boot Conventions"
            description = "Common Spring Boot conventions for BAASG services"
        }

        register("testingConventions") {
            id = "com.baasg.testing-conventions"
            implementationClass = "com.baasg.gradle.TestingConventionsPlugin"
            displayName = "BAASG Testing Conventions"
            description = "Common testing and code coverage conventions for BAASG projects"
        }

        register("codeQualityConventions") {
            id = "com.baasg.code-quality-conventions"
            implementationClass = "com.baasg.gradle.CodeQualityConventionsPlugin"
            displayName = "BAASG Code Quality Conventions"
            description = "Common code formatting and static analysis conventions for BAASG projects"
        }
    }
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        showStandardStreams = true
    }
}
