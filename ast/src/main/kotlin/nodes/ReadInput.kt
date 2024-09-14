package nodes

import visitors.ExpressionVisitor

class ReadInput(private val argument: Node, private val pos: Position) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitReadInput(this)
    }

    fun getArgument(): Node {
        return argument
    }

    fun getPos(): Position {
        return pos
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ReadInput) return false

        if (argument != other.argument) return false

        return true
    }

    override fun hashCode(): Int {
        var result = argument.hashCode()
        result = 31 * result + pos.hashCode()
        return result
    }
}
