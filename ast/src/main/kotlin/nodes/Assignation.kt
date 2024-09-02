package nodes

import ExpressionVisitor
import Position

class Assignation(private val declaration: Node, private val value: Node, private val pos: Position) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitAssignment(this)
    }

    fun getDeclaration(): Node {
        return declaration
    }

    fun getValue(): Node {
        return value
    }

    fun getPos(): Position {
        return pos
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Assignation) return false

        if (declaration != other.declaration) return false
        if (value != other.value) return false

        return true
    }
}
