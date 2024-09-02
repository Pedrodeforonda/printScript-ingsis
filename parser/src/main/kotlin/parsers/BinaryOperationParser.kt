package parsers

import Parser
import Position
import Token
import TokenType
import nodes.BinaryNode
import nodes.Node

class BinaryOperationParser(private val precedence: Int) : Infix {
    private var myToken: Token = Token("+", TokenType.PLUS, Position(0, 0))

    override fun parse(parser: Parser, left: Node, token: Token): Node {
        val right = parser.parseExpression(precedence)
        return BinaryNode(left, myToken, right, myToken.getPosition())
    }

    fun updateToken(type: Token) {
        this.myToken = type
    }

    override fun getPrecedence(): Int = precedence
}
