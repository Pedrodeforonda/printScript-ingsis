package nodes

import main.Position
import utils.ExpressionVisitor

class Literal(private val value: Any, private val pos: Position) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitLiteral(this)
    }

    fun getValue(): Any {
        return value
    }

    fun getPos(): Position {
        return pos
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Literal) return false

        if (value != other.value) return false

        return true
    }
}
