package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class OperatorStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String): Lexer {
        val currentChar = lexer.getChar()
        val tokenType = when (currentChar) {
            '+' -> TokenType.PLUS
            '-' -> TokenType.MINUS
            '*' -> TokenType.ASTERISK
            '/' -> TokenType.SLASH
            else -> TokenType.NULL_TYPE
        }
        if (tokenType != TokenType.NULL_TYPE) {
            return Lexer(
                lexer.getText(),
                lexer.getTokenStrategies(),
                lexer.getPos() + 1,
                lexer.getTokens() + Token(currentChar.toString(), tokenType),
            )
        }
        return lexer
    }
}
