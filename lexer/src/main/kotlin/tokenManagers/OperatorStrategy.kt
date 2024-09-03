package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class OperatorStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        if (lexer.getChar() == '+') {
            return Token("+", TokenType.PLUS, initialPosition)
        }
        if (lexer.getChar() == '-') {
            return Token("-", TokenType.MINUS, initialPosition)
        }
        if (lexer.getChar() == '*') {
            return Token("*", TokenType.ASTERISK, initialPosition)
        }
        if (lexer.getChar() == '/') {
            return Token("/", TokenType.SLASH, initialPosition)
        }
        return null
    }
}
