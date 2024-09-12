package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import factories.LinterFactory
import utils.PercentageCollector
import java.io.File

class AnalyzerOperation : CliktCommand(
    name = "analyze",
    help = "Analyze the source file.",
) {
    private val sourceFile: File by argument(help = "Source file to process.")
        .file(mustExist = true)

    private val configFile: File by argument(help = "Config file to use.")
        .file(mustExist = true)

    private val version: String by argument(help = "Version of the language.")

    override fun run() {
        val collector = PercentageCollector()
        val errors = LinterFactory().lintCode(sourceFile.inputStream(), version, configFile.inputStream(), collector)
        var acc = 0
        var percentage = 0.0

        for (error in errors) {
            val currentPercentage = collector.getPercentage().coerceAtMost(100.0)
            val percentageString = currentPercentage.let { "%.2f".format(it) }
            println("\u001B[31m${error.getMessage()}\u001B[0m \u001B[32m($percentageString%)\u001B[0m")
            acc++
            if (currentPercentage - percentage > 10) {
                percentage = currentPercentage
            }
        }

        if (acc == 0) {
            println("\u001B[32mNo linting errors found.\u001B[0m")
        }

        // Print the percentage of tokens processed in green color at the top
        println(
            "\u001B[32mProcessed ${collector.getPercentage().coerceAtMost(100.0)
                .let { "%.2f".format(it) }}% of tokens\u001B[0m",
        )
    }
}
