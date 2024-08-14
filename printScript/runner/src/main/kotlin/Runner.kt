package org.example

import Parser
import Token
import java.nio.file.Files
import java.nio.file.Paths

class Runner {

    fun run(path: String) {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get(path))
        val lexer = Lexer(byteArr.toString(Charsets.UTF_8))
        val tokens: List<Token> = lexer.tokenizeAll()
        val parser = Parser(tokens)
        val ast = parser.parseExpressions()
        val interpreter = Interpreter()
        interpreter.interpret(ast)
    }
}