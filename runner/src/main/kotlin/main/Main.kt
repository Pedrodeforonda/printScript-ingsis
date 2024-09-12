package main

import utils.MainStringInputProvider
import java.io.File

// TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val runner = Runner()
    val file: File = File("runner/src/main/resources/println.txt")
    val inputStream = file.inputStream()
    val version = "1.1"
    val inputs = File("runner/src/main/resources/inputs.txt").readLines().iterator()
    val envFile = File("runner/src/main/resources/env.txt")
    runner.run(inputStream, version, MainStringInputProvider(inputs), fileToMap(envFile)).toList()
    println(runner.getPercentage())
}

private fun fileToMap(file: File): Map<String, String> {
    return file.readLines().associate {
        val (key, value) = it.split("=")
        key to value
    }
}
