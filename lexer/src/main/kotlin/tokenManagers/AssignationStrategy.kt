package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class AssignationStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer): Token {
        if (lexer.getCurrentChar() == '=') {
            val tokenType = TokenType.ASSIGNATION
            lexer.goToNextPos()
            return Token("=", tokenType)
        }
        return Token("", TokenType.NULL_TYPE)
    }
}
