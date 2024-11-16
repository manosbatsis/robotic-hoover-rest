plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.spring.dependencymanager)
}

kotlin {
  compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict", "-Xemit-jvm-type-annotations") }
}

dependencies {
  implementation(project(":api"))
  implementation(rootProject.libs.wimdeblauwe.error.handling)
  implementation(platform(libs.spring.cloud.dependencies))
  implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
  implementation(libs.jackson.databind)
  implementation("io.github.openfeign:feign-okhttp")

  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
