package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class CommaStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Lexer {
        if (lexer.currentChar == ',') {
            val tokenType = TokenType.COMMA
            val newTokenList = lexer.getTokens() + Token(",", tokenType, initialPosition)
            return Lexer(
                lexer.getText(),
                lexer.getTokenStrategies(),
                lexer.getPos() + 1,
                lexer.getLexerPosition().nextColumn(),
                newTokenList,
            ) }
        return lexer
    }
}
