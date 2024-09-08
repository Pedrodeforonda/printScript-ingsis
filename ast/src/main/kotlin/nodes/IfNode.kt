package nodes

import main.Position
import utils.ExpressionVisitor

class IfNode(
    private val condition: Node,
    private val thenBlock: List<Node>?,
    private val elseBlock: List<Node>?,
    private val position: Position,
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
}
