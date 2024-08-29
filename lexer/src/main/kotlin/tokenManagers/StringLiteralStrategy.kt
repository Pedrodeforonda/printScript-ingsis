package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class StringLiteralStrategy : TokenStrategy {

    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Lexer {
        if (lexer.getChar() == '\'' || lexer.getChar() == '"') {
            var result = ""
            var newLexer = lexer.goToNextPos()
            while (newLexer.getChar() != null &&
                (newLexer.getChar() != '\'' && newLexer.getChar() != '"')
            ) {
                result += newLexer.getChar()
                newLexer = newLexer.goToNextPos()
            }
            newLexer = newLexer.goToNextPos()
            return Lexer(
                newLexer.getText(),
                newLexer.getTokenStrategies(),
                newLexer.getPos(),
                newLexer.getLexerPosition().nextColumn(),
                newLexer.getTokens() + Token(result, TokenType.STRING_LITERAL, initialPosition),
            )
        }
        return lexer
    }
}
