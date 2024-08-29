package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class RightParenStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String): Lexer {
        if (lexer.getChar() == ')') {
            val tokenType = TokenType.RIGHT_PAREN
            return Lexer(
                lexer.getText(),
                lexer.getTokenStrategies(),
                lexer.getPos() + 1,
                lexer.getLexerPosition().nextColumn(),
                lexer.getTokens() + Token(")", tokenType, lexer.getLexerPosition()),
            )
        }
        return lexer
    }
}
