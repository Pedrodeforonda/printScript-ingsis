package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class NumberStrategy : TokenStrategy {

    override fun buildToken(lexer: Lexer, result: String): Lexer {
        if (lexer.getChar() == null) { return lexer }
        if (lexer.getChar()!!.isDigit() || lexer.getChar() == '.') {
            var myresult = result
            myresult += lexer.getChar()
            val newLexer = lexer.goToNextPos()
            if (buildToken(newLexer, myresult) != newLexer) {
                return buildToken(newLexer, myresult)
            }
            return Lexer(
                newLexer.getText(),
                newLexer.getTokenStrategies(),
                newLexer.getPos(),
                newLexer.getLexerPosition().nextColumn(),
                newLexer.getTokens() + Token(myresult, TokenType.NUMBER_LITERAL, newLexer.getLexerPosition())
            )
        }
        return lexer
    }
}
