package nodes

import ExpressionVisitor
import org.example.nodes.Node

class UnaryNode(private val operator: String, private val right: Node) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitUnaryExp(this)
    }

    fun getOperator(): String {
        return operator
    }

    fun getRight(): Node {
        return right
    }
}