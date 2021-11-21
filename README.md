# bluej-jar Gradle Plugin

This is a Gradle plugin for producing JAR files which are valid [BlueJ](https://bluej.org/) project archives.

This plugin only works in Java projects, as BlueJ only supports Java and [Stride](https://stride-lang.net/). It also
only works in the `main` Source Set. Support for non-main Source Sets is planned.

For alternative build systems:
- [Script for Maven projects](https://github.com/KCLOSS/maven-bluej)

## Setup

`bluej-jar` is [available on the Gradle Plugin Portal](https://plugins.gradle.org/plugin/com.williambl.bluej_jar.bluej-jar).

Apply `bluej-jar` with:

```groovy
plugins {
  id "com.williambl.bluej_jar.bluej-jar" version "0.1"
}
```

Note that BlueJ uses Java **11**, so the bytecode compiled must be Java 11-compatible. This can be done with:
```groovy
compileJava {
    options.release = 11
}
```

## What is a BlueJ project archive?

A BlueJ project archive has several differences to a 'normal' JAR file:

 - `.java` sources are included alongside built `.class` files (the `.class` files are not even technically required!)
 - all library `.jar` files are included in a `+libs` folder (BlueJ's dependency management consists only of this `+libs` folder)
 - contents of libraries are included ('fat JAR') (it is unknown if this is required - it is likely not. However, this is how BlueJ exports JARs.)

This plugin also copies `README.TXT` and `project.bluej` files into the JAR, as BlueJ has special handling for these.

This plugin does not handle generation of `project.bluej` or `*.ctxt` files. BlueJ can import the project without them.

## Configuration

The plugin does not offer configuration at this time.

## Planned Features

 - Configuration (not include `.class` files, not include certain libraries, etc.)
 - Research whether the JAR is required to be a 'fat JAR', and if not, add an option to disable this
   - Research whether the libs must be copied to `+libs` if the JAR is fat
 - Possible `project.bluej` generation, to allow classes to be placed nicely in BlueJ's graph view
 - `README.TXT` generation from top-level readme files or from `package-info.java` javadoc.
 - Task to open the resulting JAR in BlueJ, for testing.
 - Task to change Source Set(s) used
