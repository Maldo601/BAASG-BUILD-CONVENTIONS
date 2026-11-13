
package com.baasg.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure

class JavaConventionsPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        project.pluginManager.apply("java")

        project.extensions.configure<JavaPluginExtension> {
            toolchain.languageVersion.set(
                JavaLanguageVersion.of(25)
            )
        }

        project.tasks.withType(JavaCompile::class.java).configureEach {
            options.encoding = "UTF-8"
            options.release.set(25)
        }
    }
}
