package org.example.tokenManagers

import Position
import Token
import org.example.Lexer
import org.example.TokenStrategy

class TypeAssignationStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Lexer {
        if (lexer.getChar() == ':') {
            val tokenType = TokenType.TYPE_ASSIGNATION
            val newLexer = lexer.goToNextPos()
            return Lexer(
                newLexer.getText(),
                newLexer.getTokenStrategies(),
                newLexer.getPos(),
                newLexer.getLexerPosition(),
                newLexer.getTokens() + Token(":", tokenType, initialPosition),
            )
        }
        return lexer
    }
}
