package org.example.tokenManagers

import main.Position
import main.Token
import main.TokenType
import org.example.lexer.Lexer
import org.example.lexer.TokenStrategy

class LeftParenStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        if (lexer.getChar() == '(') {
            lexer.goToNextPos()
            return Token("(", TokenType.LEFT_PAREN, initialPosition)
        }
        return null
    }
}
