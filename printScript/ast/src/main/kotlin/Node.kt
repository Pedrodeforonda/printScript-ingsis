package org.example

interface Node {
    fun accept(visitor: ExpressionVisitor): Any
}