package org.example.nodes

import Parser
import nodes.FuncCall
import nodes.Stmt
import org.example.visitors.ExpressionVisitor

class Println(val callee: Node) : Node, FuncCall {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitPrintln(this)
    }

    override fun parse(parser: Parser): Stmt {
        val identifier = parser.consume(TokenType.IDENTIFIER)
        parser.consume(TokenType.ASSIGNATION)
        val expression = parser.parseExpression(0)
        parser.consume(TokenType.SEMICOLON)
        return LetStmt(identifier.getCharArray().toString(), expression)
    }
}