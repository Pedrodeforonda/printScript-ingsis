package org.example.lexer

import lexer.Lexer
import main.Position
import main.Token

interface TokenStrategy {
    fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token?
}
