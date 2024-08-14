package org.example.tokenManagers

import Token
import TokenManager
import org.example.Lexer

class TypeAssignationManager: TokenManager {
    override fun BuildToken(lexer: Lexer?): Token {
        if (lexer!!.getCurrentChar() == ':') {
            val tokenType = TokenType.TYPE_ASSIGNATION
            val tokenChar = lexer.getCurrentChar()!!
            lexer.goToNextPos()
            return Token(charArrayOf(tokenChar), tokenType)
        }
        return Token(charArrayOf(), TokenType.NULL_TYPE)
    }
}