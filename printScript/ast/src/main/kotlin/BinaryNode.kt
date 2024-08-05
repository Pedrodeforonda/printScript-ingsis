package org.example

class BinaryNode(val left: Node, val operator: String, val right: Node) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitBinaryExp(this)
    }
}