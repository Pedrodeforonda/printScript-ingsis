package org.example.lexer

import main.Position
import main.Token

interface TokenStrategy {
    fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token?
}
