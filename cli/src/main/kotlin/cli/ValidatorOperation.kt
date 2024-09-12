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
        while (result.hasNext()) {
            val ast = result.next()
            if (ast.hasError()) {
                println(ast.getError().message)
                acc++
            }
        }
        if (acc == 0) {
            println("No parsing errors found.")
        }
    }
}
