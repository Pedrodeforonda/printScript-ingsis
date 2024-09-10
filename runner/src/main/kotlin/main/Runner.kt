package org.example.main

import main.Parser
import org.example.interpreter.Interpreter
import org.example.lexer.Lexer
import utils.InterpreterResult
import utils.PercentageCollector
import java.io.InputStream

class Runner {

    private val percentageCollector = PercentageCollector()

    fun run(src: InputStream, version: String): Sequence<InterpreterResult> = sequence {
        if (version != "1.0") {
            throw IllegalArgumentException("Invalid version")
        }

        val lexer = Lexer(src.bufferedReader(), src.available(), percentageCollector)
        val parser = Parser(lexer.tokenize().iterator())
        val interpreter = Interpreter(percentageCollector)
        val results = interpreter.interpret(parser.parseExpressions())
        results.forEach { yield(it) }
    }

    fun getPercentage() = percentageCollector.getPercentage()
}
