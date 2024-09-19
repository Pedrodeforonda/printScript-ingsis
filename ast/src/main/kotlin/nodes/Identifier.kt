package nodes

import visitors.ExpressionVisitor

class Identifier(private val name: String, private val pos: Position) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitIdentifier(this)
    }

    fun getName(): String {
        return name
    }

    fun getPos(): Position {
        return pos
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Identifier) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + pos.hashCode()
        return result
    }
}
