package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class CommaStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        if (lexer.getChar() == ',') {
            return Token(",", TokenType.COMMA, initialPosition)
        }
        return null
    }
}
