package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class StringLiteralStrategy : TokenStrategy {

    override fun buildToken(lexer: Lexer, result: String): Lexer {
        if (lexer.getCurrentChar() == '\'' || lexer.getCurrentChar() == '"') {
            var result = ""
            var newLexer = lexer.goToNextPos()
            while (newLexer.getCurrentChar() != null &&
                (newLexer.getCurrentChar() != '\'' && newLexer.getCurrentChar() != '"')
            ) {
                result += newLexer.getCurrentChar()
                newLexer = newLexer.goToNextPos()
            }
            newLexer = newLexer.goToNextPos()
            return Lexer(newLexer.getText(), newLexer.getTokenStrategies(), newLexer.getPos(), newLexer.getTokens() + Token(result, TokenType.STRING_LITERAL))
        }
        return lexer
    }
}