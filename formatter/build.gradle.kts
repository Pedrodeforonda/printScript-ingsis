plugins {
    id("buildlogic.kotlin-common-conventions")
    id("buildlogic.kotlin-library-conventions")
    id("buildlogic.kotlin-publish-package-conventions")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
    minHeapSize = "5m"
    maxHeapSize = "7m"
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation(project(":token"))
    testImplementation(project(":lexer"))
    testImplementation(project(":factory"))
    testImplementation(project(":runner"))
    testImplementation(project(":libs"))
}
