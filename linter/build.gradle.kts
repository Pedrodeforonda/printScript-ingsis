plugins {
    id("buildlogic.kotlin-common-conventions")
    id("buildlogic.kotlin-library-conventions")
    id("buildlogic.kotlin-publish-package-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
    implementation(project(":token"))
    implementation(project(":ast"))
    testImplementation(project(":factory"))
    testImplementation(project(":lexer"))
    testImplementation(project(":parser"))
}
