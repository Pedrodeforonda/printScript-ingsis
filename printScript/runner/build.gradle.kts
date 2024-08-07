plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":ast"))
    implementation(project(":token"))
    implementation(project(":lexer"))
    implementation(project(":parser"))
    implementation(project(":interpreter"))
}

tasks.test {
    useJUnitPlatform()
}