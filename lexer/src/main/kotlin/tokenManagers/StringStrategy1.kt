
package org.example.tokenManagers

import lexer.Lexer
import main.Position
import main.Token
import main.TokenType
import org.example.lexer.TokenStrategy

class StringStrategy1 : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        val currentChar = lexer.getChar() ?: return null

        if (currentChar.isLetter() || (currentChar == '_' || currentChar.isDigit()) && result.isNotEmpty()) {
            val newResult = result + currentChar
            lexer.goToNextPos()
            return buildToken(lexer, newResult, initialPosition)
        }

        return when (result) {
            "let" -> Token(result, TokenType.LET_KEYWORD, initialPosition)
            "string" -> Token(result, TokenType.STRING_TYPE, initialPosition)
            "number" -> Token(result, TokenType.NUMBER_TYPE, initialPosition)
            "println" -> Token(result, TokenType.CALL_FUNC, initialPosition)
            "readInput" -> Token(result, TokenType.READ_INPUT, initialPosition)
            "readEnv" -> Token(result, TokenType.READ_ENV, initialPosition)
            "if" -> Token(result, TokenType.IF_KEYWORD, initialPosition)
            "else" -> Token(result, TokenType.ELSE_KEYWORD, initialPosition)
            "boolean" -> Token(result, TokenType.BOOLEAN_TYPE, initialPosition)
            "const" -> Token(result, TokenType.CONST_KEYWORD, initialPosition)
            "true" -> Token(result, TokenType.BOOLEAN_LITERAL, initialPosition)
            "false" -> Token(result, TokenType.BOOLEAN_LITERAL, initialPosition)
            else -> if (result.isNotEmpty()) Token(result, TokenType.IDENTIFIER, initialPosition) else null
        }
    }
}
