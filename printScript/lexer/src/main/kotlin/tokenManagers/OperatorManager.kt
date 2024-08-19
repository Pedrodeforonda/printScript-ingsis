package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenManager

class OperatorManager: TokenManager {
    override fun buildToken(lexer: Lexer): Token {
        val currentChar = lexer.getCurrentChar()
        val tokenType = when (currentChar) {
            '+' -> TokenType.PLUS
            '-' -> TokenType.MINUS
            '*' -> TokenType.ASTERISK
            '/' -> TokenType.SLASH
            else -> TokenType.NULL_TYPE
        }
        if (tokenType != TokenType.NULL_TYPE) {
            lexer.goToNextPos()
            return Token(charArrayOf(currentChar!!), tokenType)
        }
        return Token(charArrayOf(), TokenType.NULL_TYPE)
    }
}