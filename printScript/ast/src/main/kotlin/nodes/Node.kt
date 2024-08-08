package org.example.nodes

import org.example.ExpressionVisitor

interface Node {
    fun accept(visitor: ExpressionVisitor): Any
}