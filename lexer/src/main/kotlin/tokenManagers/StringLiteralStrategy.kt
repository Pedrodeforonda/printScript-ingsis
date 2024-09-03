package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class StringLiteralStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        val currentChar = lexer.getChar() ?: return null

        if (currentChar == '"') {
            lexer.goToNextPos()
            return buildStringLiteral(lexer, "", initialPosition)
        }
        return null
    }

    private fun buildStringLiteral(lexer: Lexer, result: String, initialPosition: Position): Token? {
        val currentChar = lexer.getChar() ?: return null

        return if (currentChar == '"') {
            Token(result, TokenType.STRING_LITERAL, initialPosition)
        } else {
            val newResult = result + currentChar
            lexer.goToNextPos()
            buildStringLiteral(lexer, newResult, initialPosition)
        }
    }
}
