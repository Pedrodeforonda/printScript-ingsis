package org.example.tokenManagers

import Token
import TokenManager
import org.example.Lexer

class StringManager: TokenManager {
    override fun BuildToken(lexer: Lexer?): Token {
        if (lexer!!.getCurrentChar()!!.isLetter()) {
            var result = ""
            while (lexer.getCurrentChar() != null && lexer.getCurrentChar()!!.isLetter()) {
                result += lexer.getCurrentChar()
                lexer.goToNextPos()
            }
            return when (result) {
                "let" -> Token(result.toCharArray(), TokenType.LET_KEYWORD)
                "string" -> Token(result.toCharArray(), TokenType.STRING_TYPE)
                "number" -> Token(result.toCharArray(), TokenType.NUMBER_TYPE)
                "println" -> Token(result.toCharArray(), TokenType.CALL_FUNC)
                else -> Token(result.toCharArray(), TokenType.IDENTIFIER)
            }
        }
        return Token(charArrayOf(), TokenType.NULL_TYPE)
    }
}