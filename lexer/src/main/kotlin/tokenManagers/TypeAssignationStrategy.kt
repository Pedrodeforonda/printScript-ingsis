package org.example.tokenManagers

import Token
import org.example.Lexer
import org.example.TokenStrategy

class TypeAssignationStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String): Lexer {
        if (lexer.getCurrentChar() == ':') {
            val tokenType = TokenType.TYPE_ASSIGNATION
            val newLexer = lexer.goToNextPos()
            return Lexer(newLexer.getText(), newLexer.getTokenStrategies(), newLexer.getPos(), newLexer.getTokens() + Token(":", tokenType))
        }
        return lexer
    }
}