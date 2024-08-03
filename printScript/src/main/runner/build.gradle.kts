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
    implementation(project(":src:main:lexer"))
    implementation(project(":src:main:parser"))
    implementation(project(":src:main:interpreter"))
}

tasks.test {
    useJUnitPlatform()
}