package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class OperatorStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Lexer {
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
                lexer.getLexerPosition().nextColumn(),
                lexer.getTokens() + Token(currentChar.toString(), tokenType, initialPosition),
            )
        }
        return lexer
    }
}
