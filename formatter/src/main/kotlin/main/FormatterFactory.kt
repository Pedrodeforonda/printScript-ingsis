package main

import org.example.lexer.Lexer
import utils.PercentageCollector
import java.io.InputStream
import java.io.Writer

class FormatterFactory {

    fun format(inputStream: InputStream, config: InputStream, version: String, writer: Writer) {
        val formatter = MainFormatter()
        val configLoader = ConfigLoader()
        val standardConfig = configLoader.loadConfig<FormatterConfigReader>(config)
        val lexer = Lexer(inputStream.bufferedReader(), inputStream.available(), PercentageCollector())
        val formattedText = formatter.formatCode(lexer.tokenize(), standardConfig, writer)
    }
}
