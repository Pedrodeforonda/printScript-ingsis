package nodes

import TokenType
import ExpressionVisitor

class PrefixExpression(private val tokenType: TokenType, operand: Node): Node {

    override fun accept(visitor: ExpressionVisitor): Any {
        TODO("Not yet implemented")
    }
}