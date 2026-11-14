
package com.baasg.gradle

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.artifacts.dsl.RepositoryHandler

class CodeQualityConventionsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.pluginManager.apply("java")
        project.pluginManager.apply("com.diffplug.spotless")
        project.pluginManager.apply("checkstyle")

        val checkstyleConfig = javaClass
            .getResourceAsStream("/com/baasg/gradle/checkstyle.xml")
            ?.bufferedReader()
            ?.use { it.readText().trim() }
            ?: error("BAASG Checkstyle configuration not found")

        project.extensions.configure(SpotlessExtension::class.java) {
            java {
                target("src/**/*.java")
                googleJavaFormat("1.30.0")
                removeUnusedImports()
                trimTrailingWhitespace()
                endWithNewline()
            }
        }

        project.extensions.configure(CheckstyleExtension::class.java) {
            config = project.resources.text.fromString(checkstyleConfig)
            toolVersion = "14.4.0"
            isShowViolations = true
            isIgnoreFailures = false
        }

        project.tasks.withType(Checkstyle::class.java).configureEach {
            reports {
                xml.required.set(true)
                html.required.set(true)
            }
        }


    }
}
