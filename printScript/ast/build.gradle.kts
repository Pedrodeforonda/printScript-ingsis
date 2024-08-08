plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.printScript"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":token"))
    implementation(project(":parser"))
}

tasks.test {
    useJUnitPlatform()
}