package main

import lexer.LexerFactory
import utils.PercentageCollector
import java.io.InputStream
import java.io.Writer

class FormatterFactory {

    fun format(inputStream: InputStream, config: InputStream, version: String, writer: Writer) {
        val formatter = MainFormatter()
        val configLoader = ConfigLoader()
        val standardConfig = configLoader.loadConfig<FormatterConfigReader>(config)
        val collector = PercentageCollector()
        val lexer = LexerFactory().createLexer(inputStream, version, collector)
        formatter.formatCode(lexer.tokenize(), standardConfig, writer)
    }
}
