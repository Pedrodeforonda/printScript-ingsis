package runners

import lexer.Lexer
import lexer.TokenStrategies1
import main.Token
import org.example.lexer.ClassicTokenStrategies
import utils.PercentageCollector
import java.io.InputStream

class LexerFactory {
    internal fun createLexer(input: InputStream, version: String, percentageCollector: PercentageCollector): Lexer {
        val reader = input.bufferedReader()
        val strategies = when (version) {
            "1.0" -> ClassicTokenStrategies()
            "1.1" -> TokenStrategies1()
            else -> throw IllegalArgumentException("Unsupported version: $version")
        }
        return Lexer(reader, input.available(), percentageCollector, strategies)
    }

    fun lex(input: InputStream, version: String, percentageCollector: PercentageCollector): Sequence<Token> = sequence {
        val tokens: Sequence<Token> = LexerFactory().createLexer(input, version, percentageCollector).tokenize()
        for (token in tokens) {
            yield(token)
        }
    }
}
