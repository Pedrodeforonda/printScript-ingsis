package factories

import dataObjects.LinterResult
import main.ConfigParser
import main.Linter
import main.ParserFactory
import utils.PercentageCollector
import java.io.InputStream

class LinterFactory {

    fun lintCode(src: InputStream, version: String, config: InputStream, collector: PercentageCollector):
        Iterator<LinterResult> {
        try {
            src.available()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val linterConfig = ConfigParser.parseConfig(config)
        val lexer = LexerFactory().createLexer(src, version, collector)
        val tokens = lexer.tokenize()
        val parser = ParserFactory().createParser(version, tokens.iterator())
        val astNodes = parser.parseExpressions()
        return Linter().lint(astNodes, linterConfig).iterator()
    }
}
