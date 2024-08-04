package org.example

class Literal(val value: Any) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitLiteral(this)
        }
}