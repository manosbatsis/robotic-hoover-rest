rootProject.name = "robotic-hoover-rest"

// Find and register modules dynamically
fileTree(".")
    .matching { include("**/build.gradle.kts") }
    .map { it.parentFile.toRelativeString(rootProject.projectDir) }
    .forEach { include(":${it.replace('/', ':')}") }
