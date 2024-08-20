package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class OperatorStrategy : TokenStrategy {
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
            return Token(currentChar.toString(), tokenType)
        }
        return Token("", TokenType.NULL_TYPE)
    }
}
