package org.example

class Assignment(val declaration: Node, val value: Node) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitAssignment(this)
    }
}