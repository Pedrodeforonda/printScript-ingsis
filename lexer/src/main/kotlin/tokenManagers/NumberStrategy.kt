package org.example.tokenManagers

import main.Position
import main.Token
import main.TokenType
import org.example.lexer.Lexer
import org.example.lexer.TokenStrategy

class NumberStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        val currentChar = lexer.getChar() ?: return null

        if (currentChar.isDigit() || currentChar == '.') {
            val newResult = result + currentChar
            lexer.goToNextPos()
            return buildToken(lexer, newResult, initialPosition)
        }

        return if (result.isNotEmpty()) {
            Token(result, TokenType.NUMBER_LITERAL, initialPosition)
        } else {
            null
        }
    }
}
