package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenManager

class NumberManager: TokenManager {
    override fun buildToken(lexer: Lexer): Token {
        if (lexer.getCurrentChar()!!.isDigit()){
            var myresult = ""
            while (lexer.getCurrentChar() != null && lexer.getCurrentChar()!!.isDigit()) {
                myresult += lexer.getCurrentChar()
                lexer.goToNextPos()
            }
                return Token(myresult.toCharArray(),TokenType.NUMBER_TYPE)
            }
        return Token(charArrayOf(),TokenType.NUMBER_TYPE)
    }

}