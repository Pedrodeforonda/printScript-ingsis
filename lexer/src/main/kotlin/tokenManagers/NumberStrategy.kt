package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class NumberStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        val currentChar = lexer.getChar() ?: return null

        if (currentChar.isDigit() || currentChar == '.') {
            val newResult = result + currentChar
            lexer.goToNextPos()
            return buildToken(lexer, newResult, initialPosition)
        }

        return if (result.isNotEmpty()) {
            lexer.goToPreviousPos()
            Token(result, TokenType.NUMBER_LITERAL, initialPosition)
        } else {
            null
        }
    }
}
