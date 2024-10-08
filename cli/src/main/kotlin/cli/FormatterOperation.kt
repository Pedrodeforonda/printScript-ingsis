package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import factories.FormatterFactory
import java.io.File

class FormatterOperation : CliktCommand(
    name = "format",
    help = "Format the source file.",
) {
    private val sourceFile: File by argument(help = "Source file to process.")
        .file(mustExist = true)

    private val configFile: File by argument(help = "Config file to use.")
        .file(mustExist = true)

    private val outputFile: File by argument(help = "Output file to write the formatted code.")
        .file()

    private val version: String by argument(help = "Version of the language.")

    override fun run() {
        val outputWriter = outputFile.bufferedWriter()
        FormatterFactory().format(sourceFile.inputStream(), configFile.inputStream(), version, outputWriter)
        outputWriter.close()
        println("Formatted code written to ${outputFile.absolutePath}")
    }
}
