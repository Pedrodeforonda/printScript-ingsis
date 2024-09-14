package nodes

import visitors.ExpressionVisitor

class CallNode(private val func: String, private val arguments: List<Node>, private val pos: Position) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitCallExp(this)
    }

    fun getFunc(): String {
        return func
    }

    fun getArguments(): List<Node> {
        return arguments
    }

    fun getPos(): Position {
        return pos
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CallNode) return false

        if (func != other.func) return false
        if (arguments != other.arguments) return false

        return true
    }

    override fun hashCode(): Int {
        var result = func.hashCode()
        result = 31 * result + arguments.hashCode()
        result = 31 * result + pos.hashCode()
        return result
    }
}
