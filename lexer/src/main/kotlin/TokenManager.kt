package org.example

import Token

interface TokenManager {
    fun buildToken(lexer: Lexer): Token
}
