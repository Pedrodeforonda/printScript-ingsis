package nodes

import main.Position
import utils.ExpressionVisitor

class ReadInput(private val argument: Node, private val value: Node, private val pos: Position) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitReadInput(this)
    }

    fun getArgument(): Node {
        return argument
    }

    fun getValue(): Node {
        return value
    }

    fun getPos(): Position {
        return pos
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ReadInput) return false

        if (argument != other.argument) return false
        if (value != other.value) return false

        return true
    }
}
