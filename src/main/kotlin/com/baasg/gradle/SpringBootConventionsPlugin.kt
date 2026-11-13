
package com.baasg.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class SpringBootConventionsPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        // Apply BAASG Java conventions
        project.pluginManager.apply("com.baasg.java-conventions")

        // Apply Spring Boot
        project.pluginManager.apply("org.springframework.boot")
    }
}
