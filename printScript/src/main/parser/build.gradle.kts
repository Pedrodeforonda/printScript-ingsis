plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":src:main:ast"))
    implementation(project(":src:main:token"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}