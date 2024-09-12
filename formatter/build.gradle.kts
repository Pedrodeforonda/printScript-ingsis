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
    implementation(project(":lexer"))
    testImplementation(project(":factory"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
}
