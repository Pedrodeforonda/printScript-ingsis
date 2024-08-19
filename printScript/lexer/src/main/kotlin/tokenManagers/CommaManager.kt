package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenManager

class CommaManager: TokenManager {
    override fun buildToken(lexer: Lexer): Token {
        if (lexer.getCurrentChar() == ',') {
            val tokenType = TokenType.COMMA
            val tokenChar = lexer.getCurrentChar()!!
            lexer.goToNextPos()
            return Token(charArrayOf(tokenChar), tokenType)
        }
        return Token(charArrayOf(), TokenType.NULL_TYPE)
    }
}