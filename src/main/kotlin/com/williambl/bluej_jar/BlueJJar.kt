package com.williambl.bluej_jar

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.tasks.Jar

class BlueJJar: Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("blueJJar", Jar::class.java, Jar::blueJ)
    }
}

fun Jar.blueJ() {
    group = "build"
    description = "Build a JAR file which can be imported as a BlueJ Project"
    archiveClassifier.set("bluej")

    val java = project.extensions.getByType(JavaPluginExtension::class.java)

    val mainSourceSet = java.sourceSets.getAt("main")

    dependsOn(mainSourceSet.runtimeClasspath)

    from(mainSourceSet.output)
    from(mainSourceSet.java)

    from({
        mainSourceSet.runtimeClasspath.filter { it.name.endsWith("jar") }.map { project.zipTree(it) }
    }) {
        it.exclude("META-INF/**")
    }
    from({ mainSourceSet.runtimeClasspath.filter { it.name.endsWith("jar") } }) {
        it.into("+libs")
    }
    from({
        mainSourceSet.runtimeClasspath.filter { it.name.equals("README.TXT", true) || it.name.equals("project.bluej") }.map { project.zipTree(it) }
    })
}