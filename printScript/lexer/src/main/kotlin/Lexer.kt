package org.example

import Token

class Lexer(private val text: String) {
    private var pos = 0
    private var currentChar: Char? = null

    init {
        this.currentChar = text[pos]
    }

    private fun goToNextPos() {
        pos++
        currentChar = if (pos > text.length - 1) null else text[pos]
    }

    private fun skipWhitespace() {
        while (currentChar!!.isWhitespace()) {
            goToNextPos()
        }
    }

    private fun integer(): CharArray {
        var result = ""
        while (currentChar != null && currentChar!!.isDigit()) {
            result += currentChar
            goToNextPos()
        }
        return result.toCharArray()
    }

    fun getNextToken(): Token {
        while (currentChar != null) {
            when {
                currentChar!!.isWhitespace() -> {
                    skipWhitespace()
                    continue
                }

                currentChar!!.isDigit() -> return Token(integer(), TokenType.NUMBER_TYPE)
                currentChar in listOf('+', '-', '*', '/', '(', ')') -> {
                    val tokenType = TokenType.OPERATOR
                    val tokenChar = currentChar!!
                    goToNextPos()
                    return Token(charArrayOf(tokenChar), tokenType)
                }
                currentChar == '=' -> {
                    val tokenType = TokenType.ASSIGNATION
                    val tokenChar = currentChar!!
                    goToNextPos()
                    return Token(charArrayOf(tokenChar), tokenType)
                }
                currentChar == ';' -> {
                    val tokenType = TokenType.SEMICOLON
                    val tokenChar = currentChar!!
                    goToNextPos()
                    return Token(charArrayOf(tokenChar), tokenType)
                }
                currentChar!!.isLetter() -> {
                    var result = ""
                    while (currentChar != null && currentChar!!.isLetter()) {
                        result += currentChar
                        goToNextPos()
                    }
                    return when (result) {
                        "let" -> Token(result.toCharArray(), TokenType.LET_KEYWORD)
                        "string" -> Token(result.toCharArray(), TokenType.STRING_TYPE)
                        "number" -> Token(result.toCharArray(), TokenType.NUMBER_TYPE)
                        else -> Token(result.toCharArray(), TokenType.IDENTIFIER)
                    }
                }
                else -> {
                    throw Exception("Invalid character")
                }
            }
        }
        return Token(charArrayOf(' '), TokenType.NULL_TYPE)
    }
}