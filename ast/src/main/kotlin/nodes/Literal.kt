package nodes

import ExpressionVisitor

class Literal(private val value: Any) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitLiteral(this)
    }

    fun getValue(): Any {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Literal) return false

        if (value != other.value) return false

        return true
    }
}
