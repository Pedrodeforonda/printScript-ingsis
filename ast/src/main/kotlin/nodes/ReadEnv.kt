package nodes

import utils.ExpressionVisitor

class ReadEnv(private val name: String) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitReadEnv(this)
    }

    fun getName(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ReadEnv) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
