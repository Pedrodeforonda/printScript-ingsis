plugins {
    id("buildlogic.kotlin-library-conventions")
    id("buildlogic.kotlin-publish-package-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":ast"))
    implementation(project(":libs"))
    implementation(project(":common"))
}
