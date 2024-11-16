plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.spring.boot) apply (false)
  alias(libs.plugins.spring.dependencymanager)
}

dependencies {
  implementation(libs.jakarta.validation.api)
  implementation(rootProject.libs.springdoc.openapi.starter.webmvc.ui)
}

kotlin {
  compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict", "-Xemit-jvm-type-annotations") }
}

dependencies {
  implementation(rootProject.libs.springdoc.openapi.starter.webmvc.ui)
  implementation(rootProject.libs.wimdeblauwe.error.handling)
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
