package org.example.nodes

import org.example.visitors.ExpressionVisitor

class CallNode(private val func: String, private val arguments: String) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitCallExp(this);
    }

    fun getFunc(): String {
        return func
    }

    fun getArguments(): String {
        return arguments
    }

}