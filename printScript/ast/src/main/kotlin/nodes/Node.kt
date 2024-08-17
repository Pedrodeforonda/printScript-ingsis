package nodes

import ExpressionVisitor

interface Node {
    fun accept(visitor: ExpressionVisitor): Any
}