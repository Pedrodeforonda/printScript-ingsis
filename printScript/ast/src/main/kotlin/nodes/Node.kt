package org.example.nodes

import ExpressionVisitor

interface Node {
    fun accept(visitor: ExpressionVisitor): Any
}