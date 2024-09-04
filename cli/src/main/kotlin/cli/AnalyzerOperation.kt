package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import main.Parser
import main.Token
import main.kotlin.main.ConfigParser
import main.kotlin.main.Linter
import org.example.lexer.Lexer
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
        val config = ConfigParser.parseConfig(configFile.absolutePath)
        val lexer = Lexer(sourceFile.bufferedReader())
        val tokens: Sequence<Token> = lexer.tokenizeAll(lexer)
        val parser = Parser(tokens.iterator())
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config)
        var acc = 0
        for (error in errors) {
            println(error)
            acc++
        }
        if (acc == 0) {
            println("No errors found.")
        }
    }
}
