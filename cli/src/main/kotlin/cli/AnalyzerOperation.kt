package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import main.kotlin.ConfigParser
import main.kotlin.Linter
import java.io.File
import java.nio.file.Files

class AnalyzerOperation : CliktCommand(
    name = "analyze",
    help = "Analyze the source file.",
) {
    private val sourceFile: File by argument(help = "Source file to process.")
        .file(mustExist = true)

    private val configFile: File by argument(help = "Config file to use.")
        .file(mustExist = true)

    override fun run() {
        val config = ConfigParser.parseConfig(configFile.absolutePath)
        val errors = Linter().lint(Files.newBufferedReader(sourceFile.toPath()), config)
        if (errors.isEmpty()) {
            println("No errors found.")
        } else {
            errors.forEach { println(it) }
        }
    }
}
