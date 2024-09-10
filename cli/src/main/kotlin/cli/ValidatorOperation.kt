package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import lexer.Lexer
import main.ParseException
import main.Parser
import main.Token
import utils.PercentageCollector
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
            val length = sourceFile.inputStream().available()
            val collector = PercentageCollector()
            val reader: BufferedReader = Files.newBufferedReader(Paths.get(sourceFile.absolutePath))
            val lexer = Lexer(reader, length, collector)
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
