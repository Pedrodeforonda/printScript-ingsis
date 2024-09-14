package factories

import dataObjects.ParsingResult
import main.ParserFactory
import utils.PercentageCollector
import java.io.InputStream

class ValidatorFactory {
    fun validate(src: InputStream, version: String, collector: PercentageCollector): Sequence<ParsingResult> {
        val lexer = LexerFactory().createLexer(src, version, collector)
        val parser = ParserFactory().createParser(version, lexer.tokenize().iterator())
        return parser.parseExpressions()
    }
}
