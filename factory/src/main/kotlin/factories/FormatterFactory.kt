package factories

import main.ConfigLoader
import main.FormatterConfigReader
import main.MainFormatter
import runners.LexerFactory
import utils.PercentageCollector
import java.io.InputStream
import java.io.Writer

class FormatterFactory {

    fun format(inputStream: InputStream, config: InputStream, version: String, writer: Writer) {
        val formatter = MainFormatter()
        val configLoader = ConfigLoader()
        val standardConfig = configLoader.loadConfig<FormatterConfigReader>(config)
        val collector = PercentageCollector()
        val tokens = LexerFactory().lex(inputStream, version, collector)
        formatter.formatCode(tokens, standardConfig, writer)
    }
}
