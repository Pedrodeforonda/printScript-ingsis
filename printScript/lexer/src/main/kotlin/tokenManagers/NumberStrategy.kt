package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class NumberStrategy: TokenStrategy {
    override fun buildToken(lexer: Lexer): Token {
        if (lexer.getCurrentChar() == null) return Token("",TokenType.NULL_TYPE)
        if (lexer.getCurrentChar()!!.isDigit()){
            var myresult = ""
            while (lexer.getCurrentChar() != null && lexer.getCurrentChar()!!.isDigit()) {
                myresult += lexer.getCurrentChar()
                lexer.goToNextPos()
            }
                return Token(myresult,TokenType.NUMBER_LITERAL)
            }
        return Token("",TokenType.NULL_TYPE)
    }

}