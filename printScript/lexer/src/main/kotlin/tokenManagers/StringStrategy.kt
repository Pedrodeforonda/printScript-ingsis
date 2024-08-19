package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.`TokenStrategy`

class StringStrategy: `TokenStrategy` {
    override fun buildToken(lexer: Lexer): Token {
        if (lexer.getCurrentChar() == null) return Token("", TokenType.NULL_TYPE)
        if (lexer.getCurrentChar()!!.isLetter()) {
            var result = ""
            while (lexer.getCurrentChar() != null && lexer.getCurrentChar()!!.isLetter()) {
                result += lexer.getCurrentChar()
                lexer.goToNextPos()
            }
            return when (result) {
                "let" -> Token(result, TokenType.LET_KEYWORD)
                "string" -> Token(result, TokenType.STRING_TYPE)
                "number" -> Token(result, TokenType.NUMBER_TYPE)
                "println" -> Token(result, TokenType.CALL_FUNC)
                else -> Token(result, TokenType.IDENTIFIER)
            }
        }
        return Token("" , TokenType.NULL_TYPE)
    }
}