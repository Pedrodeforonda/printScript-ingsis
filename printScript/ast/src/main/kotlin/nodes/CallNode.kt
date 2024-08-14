package nodes

import ExpressionVisitor
import org.example.nodes.Node

class CallNode(private val func: String, private val arguments: List<Node>) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitCallExp(this);
    }

    fun getFunc(): String {
        return func
    }

    fun getArguments(): List<Node> {
        return arguments
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CallNode

        if (func != other.func) return false
        if (arguments != other.arguments) return false

        return true
    }

}