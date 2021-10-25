# bluej-jar Gradle Plugin

This is a Gradle plugin for producing JAR files which are valid [BlueJ](https://bluej.org/) project archives.

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
 - Possible `project.bluej` generation, to allow classes to be placed nicely in BlueJ's graph view
 - `README.TXT` generation from top-level readme files or from `package-info.java` javadoc.
 - Task to open the resulting JAR in BlueJ, for testing.