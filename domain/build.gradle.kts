plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.spring.dependencymanager)
}

kotlin {
  compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict", "-Xemit-jvm-type-annotations") }
}

dependencies {
  implementation(project(":api"))
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
