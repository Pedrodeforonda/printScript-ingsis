plugins {
    id("buildlogic.kotlin-common-conventions")
    id("buildlogic.kotlin-library-conventions")
    id("buildlogic.kotlin-publish-package-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":token"))
    testImplementation(project(":lexer"))
    testImplementation(project(":factory"))
    testImplementation(project(":libs"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
}
