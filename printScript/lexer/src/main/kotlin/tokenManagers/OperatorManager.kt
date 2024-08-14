package org.example.tokenManagers

import Token
import TokenManager
import org.example.Lexer

class OperatorManager: TokenManager{
    override fun BuildToken(lexer: Lexer?): Token {
        val operators : List<Char> = listOf('+', '-', '*', '/', '{', '}', '[', ']')
        for (operator in operators) {
            if (lexer!!.getCurrentChar() == operator) {
                val tokenType = TokenType.OPERATOR
                val tokenChar = lexer.getCurrentChar()!!
                lexer.goToNextPos()
                return Token(charArrayOf(tokenChar), tokenType)
            }
        }
        return Token(charArrayOf(), TokenType.NULL_TYPE)
    }

}