package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class StringStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String): Lexer { // el texto del lexer es to do
        if (lexer.getChar() == null) return lexer
        if (lexer.getChar()!!.isLetter()) {
            var newResult = result
            newResult += lexer.getChar()
            val newLexer = lexer.goToNextPos() // crea un nuevo lexer en la pos +1
            if (buildToken(newLexer, newResult) == newLexer) {
                return when (newResult) {
                    "let" -> Lexer(
                        lexer.getText(),
                        lexer.getTokenStrategies(),
                        lexer.getPos() + 1,
                        lexer.getTokens() + Token(newResult, TokenType.LET_KEYWORD),
                    )
                    "string" -> Lexer(
                        lexer.getText(),
                        lexer.getTokenStrategies(),
                        lexer.getPos() + 1,
                        lexer.getTokens() + Token(newResult, TokenType.STRING_TYPE),
                    )
                    "number" -> Lexer(
                        lexer.getText(),
                        lexer.getTokenStrategies(),
                        lexer.getPos() + 1,
                        lexer.getTokens() + Token(newResult, TokenType.NUMBER_TYPE),
                    )
                    "println" -> Lexer(
                        lexer.getText(),
                        lexer.getTokenStrategies(),
                        lexer.getPos() + 1,
                        lexer.getTokens() + Token(newResult, TokenType.CALL_FUNC),
                    )
                    else -> Lexer(
                        lexer.getText(),
                        lexer.getTokenStrategies(),
                        lexer.getPos() + 1,
                        lexer.getTokens() + Token(newResult, TokenType.IDENTIFIER),
                    )
                }
            } else {
                return buildToken(newLexer, newResult)
            }
        }
        return lexer
    }
}
