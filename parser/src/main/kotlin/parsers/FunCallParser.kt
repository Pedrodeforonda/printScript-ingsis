package parsers

import ParseException
import Parser
import Token
import nodes.CallNode
import nodes.Node

class FunCallParser : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        val arguments = mutableListOf<Node>()
        val function = CallNode(token.getCharArray(), arguments)
        val leftParen: Token = parser.lookAhead(0)
        parser.consume()
        if (leftParen.getType() != TokenType.LEFT_PAREN) {
            throw ParseException(
                "Syntax Error at ${leftParen.getPosition().getLine()}, ${leftParen.getPosition().getColumn()}" +
                    " Expected left parenthesis",
            )
        }
        val currentToken = parser.lookAhead(0)
        parser.consume()
        if (currentToken.getType() != TokenType.RIGHT_PAREN) {
            do {
                arguments.add(parser.parseExpression()) // Parse each argument
                if (parser.lookAhead(-1).getType() != TokenType.COMMA) break
                parser.consume() // Consume comma between arguments
            } while (true)
        }

        val rightParen: Token = parser.getCurrentToken()
        parser.consume()
        if (rightParen.getType() != TokenType.RIGHT_PAREN) {
            throw ParseException(
                "Syntax Error at ${rightParen.getPosition().getLine()}, ${rightParen.getPosition().getColumn()}" +
                    " Expected right parenthesis",
            )
        }
        return function
    }
}
