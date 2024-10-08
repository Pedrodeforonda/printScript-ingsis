package tokenManagers

import lexer.Lexer
import main.Position
import main.Token
import main.TokenType
import org.example.lexer.TokenStrategy

class RightParenStrategy : TokenStrategy {
    override fun buildToken(lexer: Lexer, result: String, initialPosition: Position): Token? {
        if (lexer.getChar() == ')') {
            lexer.goToNextPos()
            return Token(")", TokenType.RIGHT_PAREN, initialPosition)
        }
        return null
    }
}
