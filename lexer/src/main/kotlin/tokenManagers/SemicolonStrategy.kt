package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class SemicolonStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        if (lexer.getChar() == ';') {
            val tokenType = TokenType.SEMICOLON
            return Token(";", tokenType, initialPosition)
        }
        return null
    }
}
