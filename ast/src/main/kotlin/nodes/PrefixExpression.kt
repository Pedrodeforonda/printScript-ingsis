package nodes

import ExpressionVisitor
import TokenType

class PrefixExpression(private val tokenType: TokenType, operand: Node) : Node {

    override fun accept(visitor: ExpressionVisitor): Any {
        TODO("Not yet implemented")
    }
}
