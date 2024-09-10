package org.example.main

import java.io.File

// TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val runner = Runner()
    val file: File = File("runner/src/main/resources/println.txt")
    val inputStream = file.inputStream()
    val version = "1.0"
    runner.run(inputStream, version).toList()
    println(runner.getPercentage())
}
