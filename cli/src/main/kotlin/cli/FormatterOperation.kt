package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import main.FormatterConfigReader
import main.MainFormatter
import main.loadConfig
import org.example.lexer.Lexer
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
        val lexer: Lexer = Lexer(sourceFile.inputStream().bufferedReader())
        val tokens = lexer.tokenizeAll(lexer)
        val outputWriter = File(outputPath).bufferedWriter()
        val formattedText = MainFormatter(sourceFile.absolutePath).formatCode(tokens, config, outputWriter)
        outputWriter.close()
        println("Formatted code written to $outputPath")
    }
}
