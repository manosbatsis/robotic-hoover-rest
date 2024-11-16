plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependencymanager)
}

dependencies {
  implementation(project(":api"))
  implementation(project(":domain"))
  implementation(rootProject.libs.wimdeblauwe.error.handling)
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-web")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  testImplementation(project(":client"))
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation(platform(libs.spring.cloud.dependencies))
  testImplementation("org.springframework.cloud:spring-cloud-starter-openfeign")
  testImplementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
  compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict", "-Xemit-jvm-type-annotations") }
}

dependencies {
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
