import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
	alias(libs.plugins.kotlin.jvm)
}

group = "com.github.manosbatsis.robotichooverrest"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

allprojects{
	repositories {
		mavenCentral()
	}

	tasks.withType<Test>().configureEach {
		useJUnitPlatform()
		testLogging {
			events("passed", "skipped", "failed")
		}
		testLogging.showStandardStreams = true
		testLogging.exceptionFormat = TestExceptionFormat.FULL
	}
}