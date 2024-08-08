package org.example.visitors

import org.example.nodes.*

interface ExpressionVisitor {
    fun visitNud(nud: Node): Any
    fun visitLed(led: Node): Any
    fun visitStmt(stmt: Node): Any
}