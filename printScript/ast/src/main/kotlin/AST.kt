package org.example

class ASTNode(private val left: ASTNode?, private val right: ASTNode?, private val value: String): Node {

    override fun addLeft(node: Node): Node {
        return ASTNode(node as ASTNode, right, value)
    }

    override fun addRight(node: Node): Node {
        return ASTNode(left, node as ASTNode, value)
    }

    override fun addValue(value: String): Node {
        return ASTNode(left, right, value)
    }
}