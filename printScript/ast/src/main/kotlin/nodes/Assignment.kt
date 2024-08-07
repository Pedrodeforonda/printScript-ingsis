package org.example.nodes

import org.example.visitors.ExpressionVisitor

class Assignment(private val declaration: Node, private val value: Node) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitAssignment(this)
    }

    fun getDeclaration(): Node {
        return declaration
    }

    fun getValue(): Node {
        return value
    }
}