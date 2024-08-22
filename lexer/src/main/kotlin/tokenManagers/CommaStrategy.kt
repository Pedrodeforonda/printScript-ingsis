package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class CommaStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String): Lexer {
        if (lexer.currentChar == ',') {
            val tokenType = TokenType.COMMA
            val newTokenList = lexer.getTokens() + Token(",", tokenType)
            return Lexer(lexer.getText(), lexer.getTokenStrategies(), lexer.getPos() + 1, newTokenList)
        }
        return lexer
    }
}
