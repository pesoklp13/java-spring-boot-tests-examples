import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.20"
    
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.springframework.boot") version "2.1.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

group = "pesoklp13.examples"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val swaggerVersion = "2.9.2"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    
    compile("org.springframework.boot:spring-boot-starter-web")
    
    compile( "io.springfox:springfox-swagger2:$swaggerVersion")
    compile("io.springfox:springfox-swagger-ui:$swaggerVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

springBoot {
    mainClassName = "pesoklp13.examples.tests.dummy.DummyApplication"
}