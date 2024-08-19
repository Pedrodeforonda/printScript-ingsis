package org.example

import Token

interface TokenStrategy {
    fun buildToken(lexer: Lexer): Token
}
