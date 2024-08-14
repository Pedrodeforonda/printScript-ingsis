package parsers

import Parser
import Token
import nodes.BinaryNode
import nodes.Node

class BinaryOperationParser(private val precedence: Int) : Infix {
    override fun parse(parser: Parser, left: Node, token: Token): Node {
        val right = parser.parseExpression(precedence)
        return BinaryNode(left, token, right)
    }

    override fun getPrecedence(): Int = precedence
}


