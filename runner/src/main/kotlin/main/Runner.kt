package org.example.main

import main.Parser
import main.Token
import org.example.interpreter.Interpreter
import org.example.lexer.Lexer
import utils.InterpreterResult
import utils.PercentageCollector
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Paths

class Runner {

    fun run(path: String) {
        val collector: PercentageCollector = PercentageCollector()
        val size = Files.newInputStream(Paths.get(path)).available()
        val bufferedReader: BufferedReader = Files.newBufferedReader(Paths.get(path))
        val lexer = Lexer(bufferedReader, size, collector)
        val tokens: Sequence<Token> = lexer.tokenizeAll(lexer)
        val parser = Parser(tokens.iterator())
        val ast = parser.parseExpressions()
        val interpreter = Interpreter(collector)
        val result: Sequence<InterpreterResult> = interpreter.interpret(ast)
        for (interpreterResult in result) {
            if (interpreterResult.hasException()) println(interpreterResult.getException())
        }
    }
}
