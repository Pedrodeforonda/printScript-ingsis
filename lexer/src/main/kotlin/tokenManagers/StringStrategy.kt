package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class StringStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Lexer {
        if (lexer.getChar() == null) return lexer
        if (lexer.getChar()!!.isLetter() || lexer.getChar() == '_') {
            var newResult = result
            newResult += lexer.getChar()
            val newLexer = lexer.goToNextPos() // crea un nuevo lexer en la pos +1
            if (buildToken(newLexer, newResult, initialPosition) == newLexer) {
                return when (newResult) {
                    "let" -> Lexer(
                        lexer.getText(),
                        lexer.getTokenStrategies(),
                        lexer.getPos() + 1,
                        newLexer.getLexerPosition().nextColumn(),
                        lexer.getTokens() + Token(newResult, TokenType.LET_KEYWORD, initialPosition),
                    )
                    "string" -> Lexer(
                        lexer.getText(),
                        lexer.getTokenStrategies(),
                        lexer.getPos() + 1,
                        newLexer.getLexerPosition().nextColumn(),
                        lexer.getTokens() + Token(newResult, TokenType.STRING_TYPE, initialPosition),
                    )
                    "number" -> Lexer(
                        lexer.getText(),
                        lexer.getTokenStrategies(),
                        lexer.getPos() + 1,
                        newLexer.getLexerPosition().nextColumn(),
                        lexer.getTokens() + Token(newResult, TokenType.NUMBER_TYPE, initialPosition),
                    )
                    "println" -> Lexer(
                        lexer.getText(),
                        lexer.getTokenStrategies(),
                        lexer.getPos() + 1,
                        newLexer.getLexerPosition().nextColumn(),
                        lexer.getTokens() + Token(newResult, TokenType.CALL_FUNC, initialPosition),
                    )
                    else -> Lexer(
                        lexer.getText(),
                        lexer.getTokenStrategies(),
                        lexer.getPos() + 1,
                        newLexer.getLexerPosition().nextColumn(),
                        lexer.getTokens() + Token(newResult, TokenType.IDENTIFIER, initialPosition),
                    )
                }
            } else {
                return buildToken(newLexer, newResult, initialPosition)
            }
        }
        return lexer
    }
}
