package org.example

import Parser
import Token
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Paths

class Runner {

    fun run(path: String) {
        val bufferedReader: BufferedReader = Files.newBufferedReader(Paths.get(path))
        val lexer = Lexer(bufferedReader)
        val tokens: Sequence<Token> = lexer.tokenizeAll(lexer)
        val parser = Parser(tokens.iterator())
        val ast = parser.parseExpressions()
        val interpreter = Interpreter()
        interpreter.interpret(ast)
    }
}
