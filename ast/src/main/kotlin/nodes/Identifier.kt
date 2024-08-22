package nodes

import ExpressionVisitor

class Identifier(private val name: String) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitIdentifier(this)
    }

    fun getName(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Identifier) return false

        if (name != other.name) return false

        return true
    }
}
