package org.example.nodes

import Parser
import Token
import TokenType
import nodes.Led
import org.example.visitors.ExpressionVisitor

class Addition(val left: Node, val right: Node, val op: TokenType) : Node , Led {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitLed(this)
    }

    override fun parse(parser: Parser, token: Token, left: Node): Led {
        TODO("Not yet implemented")
    }
}