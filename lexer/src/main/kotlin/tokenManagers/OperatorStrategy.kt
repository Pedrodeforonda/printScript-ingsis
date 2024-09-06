package org.example.tokenManagers

import main.Position
import main.Token
import main.TokenType
import org.example.lexer.Lexer
import org.example.lexer.TokenStrategy

class OperatorStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        if (lexer.getChar() == '+') {
            lexer.goToNextPos()
            return Token("+", TokenType.PLUS, initialPosition)
        }
        if (lexer.getChar() == '-') {
            lexer.goToNextPos()
            return Token("-", TokenType.MINUS, initialPosition)
        }
        if (lexer.getChar() == '*') {
            lexer.goToNextPos()
            return Token("*", TokenType.ASTERISK, initialPosition)
        }
        if (lexer.getChar() == '/') {
            lexer.goToNextPos()
            return Token("/", TokenType.SLASH, initialPosition)
        }
        return null
    }
}
