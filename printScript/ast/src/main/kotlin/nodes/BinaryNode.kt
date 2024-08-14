package nodes

import Token
import ExpressionVisitor
import org.example.nodes.Node

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
}