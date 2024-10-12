
plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.spring.dependencymanager)
}

dependencies {
	implementation(project(":api"))
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xemit-jvm-type-annotations")
	}
}

dependencies {
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}