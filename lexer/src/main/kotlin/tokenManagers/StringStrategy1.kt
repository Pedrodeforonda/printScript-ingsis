package tokenManagers

import main.Position
import main.Token
import main.TokenType
import org.example.lexer.Lexer
import org.example.lexer.TokenStrategy

class StringStrategy1 : TokenStrategy {
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
            "if" -> Token(result, TokenType.IF_KEYWORD, initialPosition)
            "else" -> Token(result, TokenType.ELSE_KEYWORD, initialPosition)
            "boolean" -> Token(result, TokenType.BOOLEAN_LITERAL, initialPosition)
            "const" -> Token(result, TokenType.CONST_KEYWORD, initialPosition)
            "true" -> Token(result, TokenType.BOOLEAN_TYPE, initialPosition)
            "false" -> Token(result, TokenType.BOOLEAN_TYPE, initialPosition)
            else -> if (result.isNotEmpty()) Token(result, TokenType.IDENTIFIER, initialPosition) else null
        }
    }
}
