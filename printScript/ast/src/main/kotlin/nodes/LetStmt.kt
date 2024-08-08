package org.example.nodes

import Parser
import Token
import nodes.Stmt
import org.example.visitors.ExpressionVisitor

class LetStmt(val name: String, val value: Node) : Node, Stmt {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitLetStmt(this)
    }

    override fun parse(parser: Parser): Stmt {
        val identifier = parser.consume(TokenType.IDENTIFIER)
        parser.consume(TokenType.ASSIGNATION)
        val expression = parser.parseExpression(0)
        parser.consume(TokenType.SEMICOLON)
        return LetStmt(identifier.getCharArray().toString(), expression)
    }
}