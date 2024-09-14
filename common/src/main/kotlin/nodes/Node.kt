package nodes

import visitors.ExpressionVisitor

interface Node {
    fun accept(visitor: ExpressionVisitor): Any
}
