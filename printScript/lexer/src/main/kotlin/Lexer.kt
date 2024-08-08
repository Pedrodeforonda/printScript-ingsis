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
        if (currentChar!!.isWhitespace() || currentChar!!.equals("\r") || currentChar!!.equals("\n")) {
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

    private fun string(): CharArray {
        val quote = currentChar
        goToNextPos()
        var result = ""
        while (currentChar != null && currentChar != quote) {
            result += currentChar
            goToNextPos()
        }
        goToNextPos()
        return result.toCharArray()
    }

    fun getNextToken(): Token {
        while (currentChar != null) {
            when {
                currentChar!!.isWhitespace() || currentChar!!.equals("\r") || currentChar!!.equals("\n") -> {
                    skipWhitespace()
                    continue
                }

                currentChar!!.isDigit() -> return Token(integer(), TokenType.NUMBER_LITERAL)
                currentChar in listOf('+', '-', '*', '/', '(', ')', '{', '}', '[', ']') -> {
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
                currentChar == ':' -> {
                    val tokenType = TokenType.TYPE_ASSIGNATION
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
                (currentChar == '\'') || (currentChar == '"') -> return Token(string(), TokenType.STRING_LITERAL)
                else -> {
                    throw Exception("Invalid character")
                }
            }

        }
        return Token(charArrayOf(' '), TokenType.NULL_TYPE)
    }


    fun tokenizeAll(): List<Token> {
        val tokens = mutableListOf<Token>()
        var token: Token
        do {
            token = getNextToken()
            if (token.getType() != TokenType.NULL_TYPE) {
                tokens.add(token)
            }
        } while (token.getType() != TokenType.NULL_TYPE)
        return tokens
    }
}