package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class SemicolonStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Lexer {
        if (lexer.getChar() == ';') {
            val tokenChar = lexer.getChar()!!
            val tokenType = TokenType.SEMICOLON
            lexer.goToNextPos()
            return Lexer(
                lexer.getText(),
                lexer.getTokenStrategies(),
                lexer.getPos() + 1,
                lexer.getLexerPosition().nextLine(),
                lexer.getTokens() + Token(tokenChar.toString(), tokenType, initialPosition),
            )
        }
        return lexer
    }
}
