plugins {
    id("buildlogic.kotlin-application-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":ast"))
    implementation(project(":token"))
    implementation(project(":lexer"))
    implementation(project(":parser"))
    implementation(project(":runner"))
    implementation(project(":interpreter"))
    implementation(project(":factory"))
    implementation("com.github.ajalt.clikt:clikt:4.4.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.50")
}

application {
    mainClass.set("main.AppKt")
}

tasks.withType<JavaExec> {
    jvmArgs = listOf("-Dorg.gradle.warning.mode=none")
    standardInput = System.`in`
    standardOutput = System.`out`
}

gradle.projectsEvaluated {
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Xlint:none")
    }
}

tasks.withType<Jar> {
    dependsOn(":ast:jar")
    dependsOn(":token:jar")
    dependsOn(":lexer:jar")
    dependsOn(":parser:jar")
    dependsOn(":runner:jar")
    dependsOn(":interpreter:jar")
    dependsOn(":factory:jar")
    manifest {
        attributes["Main-Class"] = "main.AppKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}
