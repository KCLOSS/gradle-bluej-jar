package com.williambl.bluej_jar

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.tasks.Jar

class BlueJJar: Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("blueJJar", Jar::class.java) { task ->
            task.group = "build"
            task.description = "Build a JAR file which can be imported as a BlueJ Project"
            task.archiveClassifier.set("bluej")

            val java = project.extensions.getByType(JavaPluginExtension::class.java)

            val mainSourceSet = java.sourceSets.getAt("main")

            task.dependsOn(mainSourceSet.runtimeClasspath)

            task.from(mainSourceSet.output)
            task.from(mainSourceSet.java)

            task.from({ mainSourceSet.runtimeClasspath.filter { it.name.endsWith("jar") }.map { project.zipTree(it) } }) {
                it.exclude("META-INF/**")
            }
            task.from({ mainSourceSet.runtimeClasspath.filter { it.name.endsWith("jar") } }) {
                it.into("+libs")
            }
            task.from( { mainSourceSet.runtimeClasspath.filter { it.name.equals("README.TXT", true) }.map { project.zipTree(it) }})
        }
    }
}