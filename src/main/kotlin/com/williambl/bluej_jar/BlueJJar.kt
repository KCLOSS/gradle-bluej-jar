package com.williambl.bluej_jar

import org.apache.log4j.spi.LoggerFactory
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.tasks.Jar

val logger = org.slf4j.LoggerFactory.getLogger("BlueJ JAR Export")

/**
 * BlueJ JAR Export plugin for Gradle.
 */
class BlueJJar: Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("blueJJar", Jar::class.java, Jar::blueJ)
    }
}

/**
 * Set up the BlueJ JAR task.
 */
fun Jar.blueJ() {
    group = "build"
    description = "Build a JAR file which can be imported as a BlueJ Project"
    archiveClassifier.set("bluej")

    val java = project.extensions.findByType(JavaPluginExtension::class.java)
    if (java == null) {
        logger.error("Could not find Java plugin! Java is required to export BlueJ JARs.")
        return
    }

    val mainSourceSet = java.sourceSets.findByName("main")
    if (mainSourceSet == null) {
        logger.error("Could not find `main` Source Set! BlueJ JAR Export currently does not support non-main Source Set.")
        return
    }

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