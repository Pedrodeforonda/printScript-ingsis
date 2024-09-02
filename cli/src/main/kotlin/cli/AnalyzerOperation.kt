package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import main.kotlin.ConfigParser
import main.kotlin.Linter
import main.kotlin.strategies.CamelStrategy
import main.kotlin.strategies.SnakeStrategy
import java.io.File

class AnalyzerOperation : CliktCommand(
    name = "analyzer",
    help = "Analyze the source file.",
) {
    private val sourceFile: File by argument(help = "Source file to process.")
        .file(mustExist = true)

    private val configFile: File by argument(help = "Config file to use.")
        .file(mustExist = true)

    override fun run() {
        val linterStrategies = mapOf("camelCase" to CamelStrategy(), "snake_case" to SnakeStrategy())
        val config = ConfigParser.parseConfig(configFile.absolutePath)
        val linter = Linter(config, linterStrategies)
        val errors = linter.lintFile(sourceFile)
        if (errors.isEmpty()) {
            println("No errors found.")
        } else {
            errors.forEach { println(it) }
        }
    }
}
