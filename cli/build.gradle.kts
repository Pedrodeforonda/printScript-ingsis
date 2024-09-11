plugins {
    id("buildlogic.kotlin-application-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":ast"))
    implementation(project(":token"))
    implementation(project(":formatter"))
    implementation(project(":lexer"))
    implementation(project(":parser"))
    implementation(project(":linter"))
    implementation(project(":runner"))
    implementation(project(":interpreter"))
    implementation("com.github.ajalt.clikt:clikt:4.4.0")
}

application {
    mainClass.set("main.AppKt")
}

tasks.withType<JavaExec> {
    jvmArgs = listOf("-Dorg.gradle.warning.mode=none")
    standardInput = System.`in`
}

gradle.projectsEvaluated {
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Xlint:none")
    }
}
