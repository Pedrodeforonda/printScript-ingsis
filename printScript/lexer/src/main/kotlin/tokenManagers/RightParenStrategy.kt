package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class RightParenStrategy: TokenStrategy {
    override fun buildToken(lexer: Lexer): Token {
        if (lexer.getCurrentChar() == ')') {
            val tokenType = TokenType.RIGHT_PAREN
            val tokenChar = lexer.getCurrentChar()!!
            lexer.goToNextPos()
            return Token(")", tokenType)
        }
        return Token("", TokenType.NULL_TYPE)
    }
}