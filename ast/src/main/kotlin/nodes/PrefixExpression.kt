package nodes

import main.TokenType
import utils.ExpressionVisitor

class PrefixExpression(private val tokenType: TokenType, operand: Node) : Node {

    override fun accept(visitor: ExpressionVisitor): Any {
        TODO("Not yet implemented")
    }
}
