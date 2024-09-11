package nodes

import main.Position
import utils.ExpressionVisitor

class ReadEnv(private val name: String, private val pos: Position) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitReadEnv(this)
    }

    fun getName(): String {
        return name
    }

    fun getPos(): Position {
        return pos
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ReadEnv) return false

        if (name != other.name) return false
        if (pos != other.pos) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
