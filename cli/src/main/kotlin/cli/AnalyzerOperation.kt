package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import lexer.LexerFactory
import main.Parser
import main.Token
import main.kotlin.main.ConfigParser
import main.kotlin.main.Linter
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

    override fun run() {
        val src = sourceFile.inputStream()
        val collector = PercentageCollector()
        val config = ConfigParser.parseConfig(configFile.inputStream())
        val lexer = LexerFactory().createLexer(src, "1.0", collector)
        val tokens: Sequence<Token> = lexer.tokenize()
        val parser = Parser(tokens.iterator())
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config)
        var acc = 0
        for (error in errors) {
            println(error.getMessage())
            acc++
        }
        if (acc == 0) {
            println("No errors found.")
        }
    }
}
