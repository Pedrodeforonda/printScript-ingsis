package org.example

import Parser
import Token
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Paths

class Runner {

    fun run(path: String) {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get(path))
        val text: String = byteArr.toString(Charsets.UTF_8)
        val bufferedReader: BufferedReader = text.reader().buffered()
        val lexer = Lexer(bufferedReader)
        val tokens: List<Token> = lexer.tokenizeAll(lexer).toList()
        val parser = Parser(tokens)
        val ast = parser.parseExpressions()
        val interpreter = Interpreter()
        interpreter.interpret(ast)
    }
}
