package org.example

class UnaryNode(val operator: String, val right: Node) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitUnaryExp(this)
    }
}