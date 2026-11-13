
package com.baasg.gradle

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class SpringBootConventionsPluginTest {

    @TempDir
    lateinit var projectDir: Path

    @Test
    fun `should apply Spring Boot and Java conventions`() {

        Files.writeString(
            projectDir.resolve("settings.gradle.kts"),
            "rootProject.name = \"test-spring-boot-conventions\""
        )

        Files.writeString(
            projectDir.resolve("build.gradle.kts"),
            """
            plugins {
                id("com.baasg.spring-boot-conventions")
            }

            tasks.register("verifyConventions") {
                doLast {
                    check(project.pluginManager.hasPlugin("org.springframework.boot"))
                    check(project.pluginManager.hasPlugin("java"))

                    val javaExtension = project.extensions
                        .getByType(org.gradle.api.plugins.JavaPluginExtension::class.java)

                    check(javaExtension.toolchain.languageVersion.get().asInt() == 25)
                }
            }
            """.trimIndent()
        )

        val result = GradleRunner.create()
            .withProjectDir(projectDir.toFile())
            .withPluginClasspath()
            .withArguments("verifyConventions")
            .build()

        assertEquals(
            TaskOutcome.SUCCESS,
            result.task(":verifyConventions")?.outcome
        )
    }
}
