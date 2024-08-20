package parsers

import Parser
import Token
import TokenType
import nodes.BinaryNode
import nodes.Node

class BinaryOperationParser(private val precedence: Int) : Infix {
    var myToken: Token = Token("+", TokenType.PLUS)

    override fun parse(parser: Parser, left: Node, token: Token): Node {
        val right = parser.parseExpression(precedence)
        return BinaryNode(left, myToken, right)
    }

    fun updateToken(type: Token) {
        this.myToken = type
    }

    override fun getPrecedence(): Int = precedence
}
