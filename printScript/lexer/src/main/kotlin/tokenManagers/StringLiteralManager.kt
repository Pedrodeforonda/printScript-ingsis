package org.example.tokenManagers

import Token
import TokenManager
import org.example.Lexer

class StringLiteralManager: TokenManager {
    override fun BuildToken(lexer: Lexer?): Token {
        if (lexer?.getCurrentChar() == '\'' || lexer?.getCurrentChar() == '"') {
            var result = ""
            lexer.goToNextPos()
            while (lexer.getCurrentChar() != null && (lexer.getCurrentChar() != '\'' && lexer.getCurrentChar() != '"')) {
                result += lexer.getCurrentChar()
                lexer.goToNextPos()
            }
            lexer.goToNextPos()
            return Token(result.toCharArray(), TokenType.STRING_LITERAL)
        }
        return Token(charArrayOf(), TokenType.NULL_TYPE)
    }
}