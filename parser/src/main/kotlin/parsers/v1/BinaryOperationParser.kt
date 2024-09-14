package parsers.v1

import main.Infix
import main.Parser
import main.Token
import nodes.BinaryNode
import nodes.Node
import types.BinaryOperatorType

class BinaryOperationParser(private val precedence: Int) : Infix {
    private var operator: BinaryOperatorType? = null

    override fun parse(parser: Parser, left: Node, token: Token): Node {
        val right = parser.parseExpression(precedence)
        return BinaryNode(
            left,
            operator!!,
            right,
            parser.adaptPos(token.getPosition()),
        )
    }

    fun updateToken(type: BinaryOperatorType) {
        this.operator = type
    }

    override fun getPrecedence(): Int = precedence
}
