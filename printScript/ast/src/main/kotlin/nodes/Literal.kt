package org.example.nodes

import Parser
import Token
import TokenType
import nodes.Nud
import org.example.visitors.ExpressionVisitor

class Literal(val value: String, val tokenType: TokenType) : Node, Nud {
    override fun accept(visitor: ExpressionVisitor): Any {
        TODO("Not yet implemented")
    }
    override fun parse(parser: Parser, token: Token): Nud {
        return this
    }
}