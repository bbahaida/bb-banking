import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
	kotlin("plugin.jpa") version "1.5.31"
	id("com.google.cloud.tools.jib") version "2.7.1"
}

group = "com.github.bbahaida"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	jcenter()
}
val buildNumber by extra("0")
jib {
	to {
		image = "bbahaida/bb-banking"
		tags = setOf("$version", "$version.${extra["buildNumber"]}")
		auth {
			username = System.getenv("DOCKERHUB_USERNAME")
			password = System.getenv("DOCKERHUB_PASSWORD")
		}
	}
	container {
		labels = mapOf(
			"maintainer" to "Brahim Bahaida <brahim.bahaida@gmail.com>",
			"org.opencontainers.image.title" to "bb-banking",
			"org.opencontainers.image.description" to "A banking platform",
			"org.opencontainers.image.version" to "$version",
			"org.opencontainers.image.authors" to "Brahim Bahaida <brahim.bahaida@gmail.com>",
			"org.opencontainers.image.url" to "https://github.com/bbahaida/bb-banking",
			"org.opencontainers.image.licenses" to "MIT"
		)
		jvmFlags = listOf(
			"-server",
			"-Djava.awt.headless=true",
			"-XX:InitialRAMFraction=2",
			"-XX:MinRAMFraction=2",
			"-XX:MaxRAMFraction=2",
			"-XX:+UseG1GC",
			"-XX:MaxGCPauseMillis=100",
			"-XX:+UseStringDeduplication"
		)
		workingDirectory = "/app"
		ports = listOf("8080")
	}
}


dependencies {
	/*implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")*/
	implementation("org.springframework.boot:spring-boot-starter-web")
	//implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	/*implementation("org.liquibase:liquibase-core")
	implementation("org.springframework.kafka:spring-kafka")*/
	compileOnly("org.projectlombok:lombok")
	//runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
/*	testImplementation("io.projectreactor:reactor-test")

	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.springframework.security:spring-security-test")*/
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
