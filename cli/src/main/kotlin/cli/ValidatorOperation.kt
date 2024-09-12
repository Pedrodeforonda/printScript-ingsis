package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import factories.LexerFactory
import main.ParserFactory
import main.Token
import utils.PercentageCollector
import java.io.File

class ValidatorOperation : CliktCommand(
    name = "validate",
    help = "Validate the source file.",
) {
    private val sourceFile: File by argument(help = "Source file to process.")
        .file(mustExist = true)

    private val version: String by argument(help = "Version of the language.")

    override fun run() {
        val collector = PercentageCollector()
        val lexer = LexerFactory().createLexer(sourceFile.inputStream(), version, collector)
        val tokens: Sequence<Token> = lexer.tokenize()
        val parser = ParserFactory().createParser(version, tokens.iterator())
        val result = parser.parseExpressions().iterator()
        var acc = 0
        var percentage = 0.0
        while (result.hasNext()) {
            val ast = result.next()
            if (ast.hasError()) {
                val currentPercentage = collector.getPercentage().coerceAtMost(100.0)
                val percentageString = collector.getPercentage().coerceAtMost(100.0).let { "%.2f".format(it) }
                println("\u001B[31m${ast.getError().message}\u001B[0m \u001B[32m($percentageString%)\u001B[0m")
                acc++
                if (currentPercentage - percentage > 10) {
                    percentage = currentPercentage
                }
            } else {
                val currentPercentage = collector.getPercentage().coerceAtMost(100.0)
                val percentageString = collector.getPercentage().coerceAtMost(100.0).let { "%.2f".format(it) }
                if (currentPercentage - percentage > 10) {
                    println("\u001B[32m($percentageString%)\u001B[0m")
                    percentage = currentPercentage
                }
            }
        }

        if (acc == 0) {
            println("\u001B[32mNo parsing errors found.\u001B[0m")
        }

        // Print the percentage of tokens processed in green color at the top
        println(
            "\u001B[32mProcessed ${collector.getPercentage().coerceAtMost(100.0)
                .let { "%.2f".format(it) }}% of tokens\u001B[0m",
        )
    }
}
