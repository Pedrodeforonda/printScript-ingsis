package org.example.nodes

import org.example.visitors.ExpressionVisitor

interface Node {
    fun accept(visitor: ExpressionVisitor): Any
}