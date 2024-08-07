package org.example

import Parser
import Token
import java.nio.file.Files
import java.nio.file.Paths

class Runner {

    fun run(path: String) {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get(path))
        val tokens = listOf(Token("LET_KEYWORD".toCharArray(), TokenType.LET_KEYWORD), Token("IDENTIFIER".toCharArray(), TokenType.IDENTIFIER))
        val parser = Parser()
        val ast = parser.parse(tokens)
        val interpreter = Interpreter()
        interpreter.interpret(ast)
    }
}