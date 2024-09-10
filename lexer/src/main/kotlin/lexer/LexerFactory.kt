package lexer

import org.example.lexer.ClassicTokenStrategies
import org.example.lexer.Lexer
import utils.PercentageCollector
import java.io.BufferedReader
import java.io.InputStream

class LexerFactory {
    fun createLexer(input: InputStream, version: String, percentageCollector: PercentageCollector): Lexer {
        val reader = input.bufferedReader()
        val strategies = when (version) {
            "1" -> ClassicTokenStrategies()
            "1.1" -> TokenStrategies1()
            else -> throw IllegalArgumentException("Unsupported version: $version")
        }
        return Lexer(reader,input.available(), percentageCollector, strategies)
    }
}
