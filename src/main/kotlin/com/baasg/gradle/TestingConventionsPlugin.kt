
package com.baasg.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

class TestingConventionsPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        // Apply required plugins
        project.pluginManager.apply("java")
        project.pluginManager.apply("jacoco")

        // Configure JaCoCo
        project.extensions.configure(JacocoPluginExtension::class.java) {
            toolVersion = "0.8.16"
        }

        // Configure testing dependencies
        project.dependencies.add(
            "testImplementation",
            "org.junit.jupiter:junit-jupiter:6.0.1"
        )

        project.dependencies.add(
            "testRuntimeOnly",
            "org.junit.platform:junit-platform-launcher"
        )

        // Configure test tasks
        project.tasks.withType(Test::class.java).configureEach {
            useJUnitPlatform()
            finalizedBy("jacocoTestReport")
        }

        // Configure JaCoCo reports
        project.tasks.withType(JacocoReport::class.java).configureEach {
            reports {
                xml.required.set(true)
                html.required.set(true)
                csv.required.set(false)
            }
        }
    }
}
