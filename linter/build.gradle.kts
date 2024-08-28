plugins {
    id("buildlogic.kotlin-common-conventions")
    id("buildlogic.kotlin-library-conventions")
}

repositories {
    mavenCentral()
}

dependencies{
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
}