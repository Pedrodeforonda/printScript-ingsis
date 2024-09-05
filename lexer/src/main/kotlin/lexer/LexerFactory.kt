package lexer

import org.example.lexer.ClassicTokenStrategies
import org.example.lexer.Lexer
import java.io.BufferedReader

class LexerFactory {
    fun createLexer(input: BufferedReader, version: Any): Lexer {
        val strategies = when (version) {
            1, "1" -> ClassicTokenStrategies()
            1.1, "1.1" -> TokenStrategies1()
            else -> throw IllegalArgumentException("Unsupported version: $version")
        }
        return Lexer(input, strategies)
    }
}
