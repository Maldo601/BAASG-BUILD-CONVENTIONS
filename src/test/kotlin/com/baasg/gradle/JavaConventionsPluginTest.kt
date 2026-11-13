
package com.baasg.gradle

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class JavaConventionsPluginTest {

    @TempDir
    lateinit var projectDir: Path

    @Test
    fun `should configure Java 25 and UTF-8`() {

        Files.writeString(
            projectDir.resolve("settings.gradle.kts"),
            "rootProject.name = \"test-java-conventions\""
        )

        Files.writeString(
            projectDir.resolve("build.gradle.kts"),
            """
            plugins {
                id("com.baasg.java-conventions")
            }

            tasks.register("verifyConventions") {
                doLast {
                    val javaExtension = project.extensions
                        .getByType(org.gradle.api.plugins.JavaPluginExtension::class.java)

                    check(javaExtension.toolchain.languageVersion.get().asInt() == 25)

                    val compileJava = project.tasks
                        .named("compileJava", org.gradle.api.tasks.compile.JavaCompile::class.java)
                        .get()

                    check(compileJava.options.encoding == "UTF-8")
                    check(compileJava.options.release.get() == 25)
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
