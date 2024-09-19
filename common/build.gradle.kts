plugins {
    id("buildlogic.kotlin-common-conventions")
    id("buildlogic.kotlin-publish-package-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":libs"))
}
