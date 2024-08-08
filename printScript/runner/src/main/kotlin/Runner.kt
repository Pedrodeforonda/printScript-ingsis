package org.example

import Parser
import Token
import java.nio.file.Files
import java.nio.file.Paths

class Runner {

    fun run(path: String) {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get(path))
        val tokens = listOf(Token("LET_KEYWORD".toCharArray(), TokenType.LET_KEYWORD), Token("name".toCharArray(), TokenType.IDENTIFIER),
            Token(":".toCharArray(), TokenType.TYPE_ASSIGNATION),Token("string".toCharArray(), TokenType.STRING_TYPE) ,
            Token("=".toCharArray(), TokenType.ASSIGNATION) , Token("felipe".toCharArray(), TokenType.STRING_LITERAL),
            Token(";".toCharArray(), TokenType.SEMICOLON),
            Token("println".toCharArray(), TokenType.CALL_FUNC), Token("name".toCharArray(), TokenType.IDENTIFIER), Token(";".toCharArray(), TokenType.SEMICOLON))
        val parser = Parser()
        val ast = parser.parse(tokens)
        val interpreter = Interpreter()
        interpreter.interpret(ast)
    }
}