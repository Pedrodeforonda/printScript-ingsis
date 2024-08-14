package nodes

import ExpressionVisitor

class Assignation(private val declaration: Node, private val value: Node) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitAssignment(this)
    }

    fun getDeclaration(): Node {
        return declaration
    }

    fun getValue(): Node {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Assignation) return false

        if (declaration != other.declaration) return false
        if (value != other.value) return false

        return true
    }
}