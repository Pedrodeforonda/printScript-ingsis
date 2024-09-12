package main

import interpreter.Interpreter
import lexer.LexerFactory
import utils.InterpreterResult
import utils.PercentageCollector
import utils.StringInputProvider
import java.io.InputStream

class Runner {

    private val percentageCollector = PercentageCollector()

    fun run(
        src: InputStream,
        version: String,
        inputProvider: StringInputProvider,
        envMap: Map<String, String>,
        canPrint: Boolean = true,
    ): Sequence<InterpreterResult> = sequence {
        val lexer = LexerFactory().createLexer(src, version, percentageCollector)
        val parser = ParserFactory().createParser(version, lexer.tokenize().iterator())
        val interpreter = Interpreter(percentageCollector, inputProvider, envMap, canPrint)
        val results = interpreter.interpret(parser.parseExpressions())
        results.forEach { yield(it) }
    }

    fun getPercentage() = percentageCollector.getPercentage()
}
