package nodes

import ExpressionVisitor
import Token

class BinaryNode(private val left: Node, private val operator: Token, private val right: Node) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitBinaryExp(this)
    }

    fun getLeft(): Node {
        return left
    }

    fun getOperator(): Token {
        return operator
    }

    fun getRight(): Node {
        return right
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BinaryNode) return false

        if (left != other.left) return false
        if (operator != other.operator) return false
        if (right != other.right) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + operator.hashCode()
        result = 31 * result + right.hashCode()
        return result
    }
}
