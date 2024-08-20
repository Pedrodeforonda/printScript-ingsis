plugins {
    id("buildlogic.kotlin-common-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":token"))
    implementation(project(":lexer"))
    implementation(project(":parser"))
    implementation(project(":interpreter"))
}
