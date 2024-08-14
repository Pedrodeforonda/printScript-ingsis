package org.example.tokenManagers

import Token
import TokenManager
import org.example.Lexer

class NumberManager: TokenManager {
    override fun BuildToken(lexer: Lexer): Token {

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