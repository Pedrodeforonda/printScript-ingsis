package org.example.nodes

import org.example.ExpressionVisitor

class Literal(private val value: Any) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitLiteral(this)
    }

    fun getValue(): Any {
        return value
    }
}