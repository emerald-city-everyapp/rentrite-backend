import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "com.emeraldeveryapp"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework:spring-jdbc")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")

	// Kotlin
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")

	// GCP
	implementation(platform("com.google.cloud:spring-cloud-gcp-dependencies:5.3.0"))
	implementation("com.google.cloud.sql:postgres-socket-factory:1.18.1")
	implementation("com.google.cloud:spring-cloud-gcp-starter")
	implementation("org.postgresql:postgresql:42.2.18")
	
	// Testing
	testImplementation("io.zonky.test:embedded-database-spring-test:2.5.1")
	testImplementation("io.zonky.test:embedded-postgres:1.2.10")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
  	testImplementation("org.mockito:mockito-core")
	testImplementation("org.apache.httpcomponents.client5:httpclient5")
	testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named<Jar>("jar") {
	enabled = false
}
