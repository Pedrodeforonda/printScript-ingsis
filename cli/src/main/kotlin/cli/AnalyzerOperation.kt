package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import factories.LinterFactory
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
        val errors = LinterFactory().lintCode(sourceFile.inputStream(), version, configFile.inputStream())
        var acc = 0
        for (error in errors) {
            println(error.getMessage())
            acc++
        }
        if (acc == 0) {
            println("No linting errors found.")
        }
    }
}
