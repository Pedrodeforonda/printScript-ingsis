package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class AssignationStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String): Lexer {
        if (lexer.currentChar == '=') {
            val tokenType = TokenType.ASSIGNATION
            val newTokenList = lexer.getTokens() + Token("=", tokenType, lexer.getLexerPosition())
            return Lexer(lexer.getText(),
                lexer.getTokenStrategies(),
                lexer.getPos() + 1,
                lexer.getLexerPosition().nextColumn(),
                newTokenList)
        }
        return lexer
    }
}
