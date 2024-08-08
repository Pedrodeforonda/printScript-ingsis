package org.example.nodes

import org.example.ExpressionVisitor

class CallNode(private val func: String, private val arguments: List<Node>) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitCallExp(this);
    }

    fun getFunc(): String {
        return func
    }

    fun getArguments(): List<Node> {
        return arguments
    }

}