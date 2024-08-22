package org.example

interface TokenStrategy {
    fun buildToken(lexer: Lexer, result: String): Lexer
}
