package org.example.tokenManagers

import main.Position
import main.Token
import main.TokenType
import org.example.lexer.Lexer
import org.example.lexer.TokenStrategy

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
