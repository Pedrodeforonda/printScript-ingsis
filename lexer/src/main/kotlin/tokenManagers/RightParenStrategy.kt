package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class RightParenStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        if (lexer.getChar() == ')') {
            return Token(")", TokenType.RIGHT_PAREN, initialPosition)
        }
        return null
    }
}
