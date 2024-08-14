package nodes

import ExpressionVisitor
import org.example.nodes.Node

class Identifier(private val value: String) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitIdentifier(this);
    }

    fun getValue(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Identifier) return false

        if (value != other.value) return false

        return true
    }
}