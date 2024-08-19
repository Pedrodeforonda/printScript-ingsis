package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenManager

class LeftParenManager: TokenManager {
    override fun buildToken(lexer: Lexer): Token {
        if (lexer.getCurrentChar() == '(') {
            val tokenType = TokenType.LEFT_PAREN
            val tokenChar = lexer.getCurrentChar()!!
            lexer.goToNextPos()
            return Token(charArrayOf(tokenChar), tokenType)
        }
        return Token(charArrayOf(), TokenType.NULL_TYPE)
    }
}