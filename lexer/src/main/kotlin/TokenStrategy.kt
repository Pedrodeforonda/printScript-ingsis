package org.example

import Position

interface TokenStrategy {
    fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Lexer
}
