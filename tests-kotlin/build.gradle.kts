import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.21"
    
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.springframework.boot") version "2.1.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
}

group = "pesoklp13.examples"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val swaggerVersion = "2.9.2"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")

    compile("com.fasterxml.jackson.core:jackson-databind")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    
    compile( "io.springfox:springfox-swagger2:$swaggerVersion")
    compile("io.springfox:springfox-swagger-ui:$swaggerVersion")

    testCompile("junit", "junit", "4.12")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("Xjsr305=strict")
}

springBoot {
    mainClassName = "pesoklp13.examples.tests.dummy.app.DummyApplication"
}