package org.example.nodes

import org.example.ExpressionVisitor

class GroupingNode(private val node: Node) : Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitGroupingExp(this)
    }

    fun getNode(): Node {
        return node
    }
}