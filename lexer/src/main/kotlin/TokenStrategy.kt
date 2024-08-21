package org.example

import Token

interface TokenStrategy {
    fun buildToken(lexer: Lexer, result: String): Lexer
}
