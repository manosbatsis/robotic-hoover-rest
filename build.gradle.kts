import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.spotless)
}

group = "com.github.manosbatsis.robotichooverrest"

version = "0.0.1-SNAPSHOT"

java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }

spotless {
  kotlin {
    target("**/*.kt")
    // version, style and all configurations here are optional
    ktfmt("0.53").googleStyle().configure {
      it.setMaxWidth(100)
      it.setBlockIndent(4)
      it.setContinuationIndent(4)
      it.setRemoveUnusedImports(true)
      it.setManageTrailingCommas(false)
    }
    trimTrailingWhitespace()
    indentWithSpaces()
    endWithNewline()
    licenseHeaderFile("$rootDir/etc/source-header.txt")
  }
  kotlinGradle {
    target("**/*.gradle.kts")
    ktfmt()
  }
}

allprojects {
  repositories { mavenCentral() }

  tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging { events("passed", "skipped", "failed") }
    testLogging.showStandardStreams = true
    testLogging.exceptionFormat = TestExceptionFormat.FULL
  }
}
