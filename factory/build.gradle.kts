plugins {
    id("buildlogic.kotlin-common-conventions")
    id("buildlogic.kotlin-publish-package-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":formatter"))
    implementation(project(":linter"))
    implementation(project(":runner"))
    implementation(project(":libs"))
    implementation(project(":common"))
}
