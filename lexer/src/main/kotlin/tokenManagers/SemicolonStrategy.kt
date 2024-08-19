package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class SemicolonStrategy: TokenStrategy {
    override fun buildToken(lexer: Lexer): Token {
        if (lexer.getCurrentChar() == ';') {
            val tokenChar = lexer.getCurrentChar()!!
            val tokenType = TokenType.SEMICOLON
            lexer.goToNextPos()
            return Token(";", tokenType)
        }
        return Token("", TokenType.NULL_TYPE)
    }
}