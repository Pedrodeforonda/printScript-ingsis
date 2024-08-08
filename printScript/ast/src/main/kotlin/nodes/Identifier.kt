package org.example.nodes

import org.example.ExpressionVisitor

class Identifier(private val value: String) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitIdentifier(this);
    }

    fun getValue(): String {
        return value
    }
}