plugins {
    id("buildlogic.kotlin-common-conventions")
    id("buildlogic.kotlin-publish-package-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":token"))
    implementation(project(":ast"))
    implementation(project(":lexer"))
    implementation(project(":parser"))
    implementation(project(":formatter"))
    implementation(project(":linter"))
}
