package org.example

import Position
import Token

interface TokenStrategy {
    fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token?
}
