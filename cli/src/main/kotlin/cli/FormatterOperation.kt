package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import lexer.LexerFactory
import main.ConfigLoader
import main.FormatterConfigReader
import main.MainFormatter
import utils.PercentageCollector
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
        val configLoader = ConfigLoader()
        val collector = PercentageCollector()
        val config = configLoader.loadConfig<FormatterConfigReader>(configFile.inputStream())
        val lexer: Lexer = LexerFactory().createLexer(sourceFile.inputStream(), "1.0", collector)
        val tokens = lexer.tokenize()
        val outputWriter = File(outputPath).bufferedWriter()
        val formattedText = MainFormatter().formatCode(tokens, config, outputWriter)
        outputWriter.close()
        println("Formatted code written to $outputPath")
    }
}
