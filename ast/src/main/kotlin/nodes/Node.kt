package nodes

import utils.ExpressionVisitor

interface Node {
    fun accept(visitor: ExpressionVisitor): Any
}
