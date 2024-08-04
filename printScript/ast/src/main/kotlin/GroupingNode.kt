package org.example

class GroupingNode(val node: Node) : Node {
    override fun  accept(visitor: ExpressionVisitor): Any {
        return visitor.visitGroupingExp(this)
    }
}