package org.example

import Token
import junit.framework.TestCase.assertEquals
import java.nio.file.Files
import java.nio.file.Paths

class LexerTest {

    @org.junit.Test
    fun testTokenizeAll() {
        val lexer = Lexer("tu string de entrada completo")
        val actualTokens = lexer.tokenizeAll()
        val expectedTokens = listOf<Token>(
            Token(charArrayOf('t', 'u'), TokenType.IDENTIFIER),
            Token(charArrayOf('s', 't', 'r', 'i', 'n', 'g'), TokenType.STRING_TYPE),
            Token(charArrayOf('d', 'e'), TokenType.IDENTIFIER),
            Token(charArrayOf('e', 'n', 't', 'r', 'a', 'd', 'a'), TokenType.IDENTIFIER),
            Token(charArrayOf('c', 'o', 'm', 'p', 'l', 'e', 't', 'o'), TokenType.IDENTIFIER)
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @org.junit.Test
    fun testTokenizeCodeLines() {
        val lexer = Lexer("let a: number = 12;")
        val actualTokens = lexer.tokenizeAll()

        val expectedTokens = listOf<Token>(
            Token(charArrayOf('l', 'e', 't'), TokenType.LET_KEYWORD),
            Token(charArrayOf('a'), TokenType.IDENTIFIER),
            Token(charArrayOf(':'), TokenType.TYPE_ASSIGNATION),
            Token(charArrayOf('n', 'u', 'm', 'b', 'e', 'r'), TokenType.NUMBER_TYPE),
            Token(charArrayOf('='), TokenType.ASSIGNATION),
            Token(charArrayOf('1', '2'), TokenType.NUMBER_LITERAL),
            Token(charArrayOf(';'), TokenType.SEMICOLON),
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @org.junit.Test
    fun testTokenizeCodeLine2() {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get("src/main/resources/test1.txt"))
        val text: String = byteArr.toString(Charsets.UTF_8)
        val lexer = Lexer(text)
        val actualTokens = lexer.tokenizeAll()

        val expectedTokens = listOf<Token>(
            Token(charArrayOf('l', 'e', 't'), TokenType.LET_KEYWORD),
            Token(charArrayOf('a'), TokenType.IDENTIFIER),
            Token(charArrayOf(':'), TokenType.TYPE_ASSIGNATION),
            Token(charArrayOf('s', 't', 'r', 'i', 'n', 'g'), TokenType.STRING_TYPE),
            Token(charArrayOf('='), TokenType.ASSIGNATION),
            Token(charArrayOf('m', 'o', 'n', 'o', 's'), TokenType.STRING_LITERAL),
            Token(charArrayOf(';'), TokenType.SEMICOLON),
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @org.junit.Test
    fun testTokenizeCodeLine3() {
        val lexer = Lexer("a = a / b;")
        val actualTokens = lexer.tokenizeAll()

        val expectedTokens = listOf<Token>(
            Token(charArrayOf('a'), TokenType.IDENTIFIER),
            Token(charArrayOf('='), TokenType.ASSIGNATION),
            Token(charArrayOf('a'), TokenType.IDENTIFIER),
            Token(charArrayOf('/'), TokenType.OPERATOR),
            Token(charArrayOf('b'), TokenType.IDENTIFIER),
            Token(charArrayOf(';'), TokenType.SEMICOLON),
        )

        assertEquals(expectedTokens, actualTokens)
    }
}