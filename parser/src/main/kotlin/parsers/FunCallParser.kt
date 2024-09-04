package parsers

import main.ParseException
import main.Parser
import main.Token
import main.TokenType
import nodes.CallNode
import nodes.Node

class FunCallParser : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        val arguments = mutableListOf<Node>()
        val function = CallNode(token.getText(), arguments, token.getPosition())
        val leftParen: Token = parser.consume()
        if (leftParen.getType() != TokenType.LEFT_PAREN) {
            throw ParseException(
                "Syntax Error at ${leftParen.getPosition().getLine()}, ${leftParen.getPosition().getColumn()}" +
                    " Expected left parenthesis",
            )
        }
        val currentToken = parser.consume()
        if (currentToken.getType() != TokenType.RIGHT_PAREN) {
            do {
                arguments.add(parser.parseExpression()) // Parse each argument
                if (parser.getCurrentToken().getType() != TokenType.COMMA) {
                    break
                }
                parser.consume() // Consume comma between arguments
            } while (true)
        }

        val rightParen: Token = parser.getCurrentToken()

        if (rightParen.getType() != TokenType.RIGHT_PAREN) {
            throw ParseException(
                "Syntax Error at ${rightParen.getPosition().getLine()}, ${rightParen.getPosition().getColumn()}" +
                    " Expected right parenthesis",
            )
        }
        parser.consume()
        return function
    }
}
