package org.example.nodes

import org.example.ExpressionVisitor

class BinaryNode(private val left: Node, private val operator: String, private val right: Node) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitBinaryExp(this)
    }

    fun getLeft(): Node {
        return left
    }

    fun getOperator(): String {
        return operator
    }

    fun getRight(): Node {
        return right
    }
}