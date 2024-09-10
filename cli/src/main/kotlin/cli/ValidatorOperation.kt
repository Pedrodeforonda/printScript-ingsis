package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import lexer.LexerFactory
import main.ParseException
import main.Parser
import main.Token
import utils.PercentageCollector
import java.io.File

class ValidatorOperation : CliktCommand(
    name = "validate",
    help = "Validate the source file.",
) {
    private val sourceFile: File by argument(help = "Source file to process.")
        .file(mustExist = true)

    override fun run() {
        try {
            val collector = PercentageCollector()
            val lexer: Lexer = LexerFactory().createLexer(sourceFile.inputStream(), "1.0", collector)
            val tokens: Sequence<Token> = lexer.tokenize()
            val parser = Parser(tokens.iterator())
            val result = parser.parseExpressions().iterator()
            while (result.hasNext()) {
                result.next()
            }
        } catch (e: ParseException) {
            println(e.message)
        }
    }
}
