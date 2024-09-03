package cli

import ParseException
import Parser
import Token
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import org.example.Lexer
import java.io.BufferedReader
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class ValidatorOperation : CliktCommand(
    name = "validate",
    help = "Validate the source file.",
) {
    private val sourceFile: File by argument(help = "Source file to process.")
        .file(mustExist = true)

    override fun run() {
        try {
            val reader: BufferedReader = Files.newBufferedReader(Paths.get(sourceFile.absolutePath))
            val lexer = Lexer(reader)
            val tokens: Sequence<Token> = lexer.tokenizeAll(lexer)
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
