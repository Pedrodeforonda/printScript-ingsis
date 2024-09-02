package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class NumberStrategy : TokenStrategy {

    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Lexer {
        if (lexer.getChar() == null) { return lexer }
        if (lexer.getChar()!!.isDigit() || lexer.getChar() == '.') {
            var myresult = result
            myresult += lexer.getChar()
            val newLexer = lexer.goToNextPos()
            if (buildToken(newLexer, myresult, initialPosition) != newLexer) {
                return buildToken(newLexer, myresult, initialPosition)
            }
            return Lexer(
                newLexer.getText(),
                newLexer.getTokenStrategies(),
                newLexer.getPos(),
                newLexer.getLexerPosition(),
                newLexer.getTokens() + Token(myresult, TokenType.NUMBER_LITERAL, initialPosition),
            )
        }
        return lexer
    }
}
