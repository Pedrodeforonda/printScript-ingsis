package org.example.tokenManagers

import Token
import TokenManager
import org.example.Lexer

class AssignationManager: TokenManager {
    override fun BuildToken(lexer: Lexer?): Token {
        if (lexer?.getCurrentChar() == '=') {
            val tokenChar = lexer.getCurrentChar()!!
            val tokenType = TokenType.ASSIGNATION
            lexer.goToNextPos()
            return Token(charArrayOf(tokenChar), tokenType)
        }
        return Token(charArrayOf(), TokenType.NULL_TYPE)
    }
}