package linter

import main.kotlin.ConfigParser
import main.kotlin.Linter
import java.io.File

fun main(args: Array<String>) {
    val config = ConfigParser.parseConfig("config.yaml")
    val linter = Linter(config)
    val errors = linter.lintFile(File("path/to/your/file.kt"))
    errors.forEach { println(it) }
}