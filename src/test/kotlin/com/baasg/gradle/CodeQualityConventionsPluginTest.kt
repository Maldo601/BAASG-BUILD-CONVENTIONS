
package com.baasg.gradle

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class CodeQualityConventionsPluginTest {

    @TempDir
    lateinit var projectDir: Path

    @Test
    fun `should configure Spotless and Checkstyle`() {

        Files.writeString(
            projectDir.resolve("settings.gradle.kts"),
            "rootProject.name = \"test-code-quality-conventions\""
        )

        Files.writeString(
            projectDir.resolve("build.gradle.kts"),
            """
            plugins {
                id("com.baasg.code-quality-conventions")
            }

            repositories {
                mavenCentral()
            }

            tasks.register("verifyConventions") {
                doLast {
                    check(project.pluginManager.hasPlugin("java"))
                    check(project.pluginManager.hasPlugin("com.diffplug.spotless"))
                    check(project.pluginManager.hasPlugin("checkstyle"))

                    val checkstyleExtension = project.extensions
                        .getByType(org.gradle.api.plugins.quality.CheckstyleExtension::class.java)

                    check(checkstyleExtension.toolVersion == "14.4.0")

                    check(project.tasks.names.contains("spotlessJava"))
                    check(project.tasks.names.contains("spotlessCheck"))
                    check(project.tasks.names.contains("checkstyleMain"))
                }
            }
            """.trimIndent()
        )

        val javaSource = projectDir.resolve(
            "src/main/java/com/baasg/Sample.java"
        )

        Files.createDirectories(javaSource.parent)

        Files.writeString(
            javaSource,
            """
            package com.baasg;

            public class Sample {
                public void run() {
                    System.out.println("BAASG");
                }
            }
            """.trimIndent() + "\n"
        )

        val result = GradleRunner.create()
            .withProjectDir(projectDir.toFile())
            .withPluginClasspath()
            .withArguments(
                "verifyConventions",
                "spotlessApply",
                "spotlessCheck",
                "checkstyleMain",
                "--stacktrace",
                "--info"
            )
            .forwardOutput()
            .build()

        fun assertTaskSucceeded(taskName: String) {
            val outcome = result.task(":$taskName")?.outcome

            assertTrue(
                outcome == TaskOutcome.SUCCESS ||
                    outcome == TaskOutcome.UP_TO_DATE,
                "Task $taskName did not succeed. Actual outcome: $outcome"
            )
        }

        assertTaskSucceeded("verifyConventions")
        assertTaskSucceeded("spotlessApply")
        assertTaskSucceeded("spotlessCheck")
        assertTaskSucceeded("checkstyleMain")

        val formattedSource = Files.readString(javaSource)

        println("========== FORMATTED JAVA SOURCE ==========")
        println(formattedSource.replace(" ", "·"))
        println("===========================================")

        assertTrue(
            formattedSource.contains("    public void run() {"),
            "Expected 4 spaces before the method declaration"
        )

        assertTrue(
            formattedSource.contains("        System.out.println"),
            "Expected 8 spaces before the method body statement"
        )
    }
}
