package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class LeftParenStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String): Lexer {
        if (lexer.getChar() == '(') {
            val tokenType = TokenType.LEFT_PAREN
            val newTokenList = lexer.getTokens() + Token("(", tokenType)
            return Lexer(lexer.getText(), lexer.getTokenStrategies(), lexer.getPos() + 1, newTokenList)
        }
        return lexer
    }
}
