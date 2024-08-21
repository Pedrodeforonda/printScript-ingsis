package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class NumberStrategy : TokenStrategy {

    override fun buildToken(lexer: Lexer, result: String): Lexer {
        if (lexer.getCurrentChar() == null) {return lexer}
        if (lexer.getCurrentChar()!!.isDigit()) {
            var myresult = result
            myresult += lexer.getCurrentChar()
            val newLexer = lexer.goToNextPos()
            return Lexer(newLexer.getText(), newLexer.getTokenStrategies(), newLexer.getPos(), newLexer.getTokens() + Token(myresult, TokenType.NUMBER_LITERAL))
        }
        return lexer
    }
}