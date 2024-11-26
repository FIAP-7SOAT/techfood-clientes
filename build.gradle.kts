import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.9.24"

	kotlin("jvm") version kotlinVersion
	kotlin("plugin.noarg") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion

	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.flywaydb.flyway") version "10.13.0"
	id("jacoco")
}

group = "br.com.fiap"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

val kotlinLoggingVersion = "3.0.5"
val mockkVersion = "1.13.4"
val springCloudVersion = "2023.0.1"
val springMockk = "4.0.2"

dependencies {
	// Spring and Kotlin Dependencies
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-undertow")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
	implementation("org.postgresql:postgresql:42.3.3")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	// Cucumber Dependencies for Java (JUnit, Spring)
	testImplementation("io.cucumber:cucumber-java:7.14.0")
	testImplementation("io.cucumber:cucumber-spring:7.14.0")
	testImplementation("io.cucumber:cucumber-junit:7.14.0") // For running with JUnit 4
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0") // JUnit 5 API
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0") // JUnit engine for execution

	// Mocking and Test Utilities
	testImplementation("com.h2database:h2")
	testImplementation("com.ninja-squad:springmockk:$springMockk")
	testImplementation("com.tngtech.archunit:archunit-junit5:1.3.0")
	testImplementation("io.mockk:mockk:$mockkVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test") // Spring Boot Test
	testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock") // Wiremock for contract testing
	testImplementation("org.mockito:mockito-core:5.5.0")
	testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform() // Ensure JUnit is used for tests
}

tasks.jacocoTestReport {
	dependsOn("test") // Make sure that tests are executed before generating the report

	reports {
		xml.required.set(true) // Generate XML report
		html.required.set(true) // Generate HTML report
	}

	// Direct configuration of classDirectories without afterEvaluate
	classDirectories.setFrom(
		fileTree("$buildDir/classes/kotlin/main") {
			exclude(
				"**/app/**", // Excluding certain packages from coverage
				"**/dto/**",
				"**/mapper/**",
				"**/configuration/**",
				"**/generated/**"
			)
		}
	)
}

tasks.jacocoTestCoverageVerification {
	dependsOn(tasks.test)

	violationRules {
		rule {
			limit {
				minimum = 0.80.toBigDecimal() // 80% minimum coverage
			}
		}
	}
}

// Explicitly include your test runner for Cucumber
tasks.test {
	include("**/RunCucumberTest.class") // Include the Cucumber test runner
}
