plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.printScript"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":ast"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}