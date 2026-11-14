
package com.baasg.gradle

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class TestingConventionsPluginTest {

    @TempDir
    lateinit var projectDir: Path

    @Test
    fun `should configure JUnit and JaCoCo`() {

        Files.writeString(
            projectDir.resolve("settings.gradle.kts"),
            "rootProject.name = \"test-testing-conventions\""
        )

        Files.writeString(
            projectDir.resolve("build.gradle.kts"),
            """
            plugins {
                id("com.baasg.testing-conventions")
            }

            tasks.register("verifyConventions") {
                doLast {
                    check(project.pluginManager.hasPlugin("java"))
                    check(project.pluginManager.hasPlugin("jacoco"))

                    val jacocoExtension = project.extensions
                        .getByType(org.gradle.testing.jacoco.plugins.JacocoPluginExtension::class.java)

                    check(jacocoExtension.toolVersion == "0.8.16")

                    val testTask = project.tasks
                        .named("test", org.gradle.api.tasks.testing.Test::class.java)
                        .get()

                    check(testTask.options is org.gradle.api.tasks.testing.junitplatform.JUnitPlatformOptions)
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
