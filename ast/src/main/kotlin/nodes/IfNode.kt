package nodes

import main.Position
import utils.ExpressionVisitor

class IfNode(
    private val condition: Node,
    private val thenBlock: List<Node>?,
    private val elseBlock: List<Node>?,
    private val pos: Position,
) : Node {
    override fun accept(visitor: ExpressionVisitor) = visitor.visitIf(this)

    fun getCondition(): Node {
        return condition
    }

    fun getThenBlock(): List<Node>? {
        return thenBlock
    }

    fun getElseBlock(): List<Node>? {
        return elseBlock
    }

    fun getPos(): Position {
        return pos
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IfNode) return false

        if (condition != other.condition) return false
        if (thenBlock != other.thenBlock) return false
        if (elseBlock != other.elseBlock) return false

        return true
    }

    override fun hashCode(): Int {
        var result = condition.hashCode()
        result = 31 * result + (thenBlock?.hashCode() ?: 0)
        result = 31 * result + (elseBlock?.hashCode() ?: 0)
        return result
    }
}
