package tokenManagers

import lexer.Lexer
import main.Position
import main.Token
import main.TokenType
import org.example.lexer.TokenStrategy

class SemicolonStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        if (lexer.getChar() == ';') {
            lexer.goToNextPos()
            val tokenType = TokenType.SEMICOLON
            return Token(";", tokenType, initialPosition)
        }
        return null
    }
}
