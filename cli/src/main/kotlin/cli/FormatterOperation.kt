package cli

import FormatterConfigReader
import MainFormatter
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import loadConfig
import java.io.File

class FormatterOperation : CliktCommand(
    name = "format",
    help = "Format the source file.",
) {
    private val sourceFile: File by argument(help = "Source file to process.")
        .file(mustExist = true)

    private val configFile: File by argument(help = "Config file to use.")
        .file(mustExist = true)

    override fun run() {
        val outputPath = "src/main/resources/formattedCode.txt"
        val config = loadConfig<FormatterConfigReader>(configFile.absolutePath)
        val formattedText = MainFormatter().formatCode(sourceFile.readText(), config)
        File(outputPath).writeText(formattedText)
        println("Formatted code written to $outputPath")
    }
}
