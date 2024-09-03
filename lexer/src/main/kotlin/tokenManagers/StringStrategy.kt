package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class StringStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        val currentChar = lexer.getChar() ?: return null

        if (currentChar.isLetter() || (currentChar == '_' || currentChar.isDigit()) && result.isNotEmpty()) {
            val newResult = result + currentChar
            lexer.goToNextPos()
            return buildToken(lexer, newResult, initialPosition)
        }
        if (result.isNotEmpty()) lexer.goToPreviousPos()
        return when (result) {
            "let" -> Token(result, TokenType.LET_KEYWORD, initialPosition)
            "string" -> Token(result, TokenType.STRING_TYPE, initialPosition)
            "number" -> Token(result, TokenType.NUMBER_TYPE, initialPosition)
            "println" -> Token(result, TokenType.CALL_FUNC, initialPosition)
            else -> if (result.isNotEmpty()) Token(result, TokenType.IDENTIFIER, initialPosition) else null
        }
    }
}
