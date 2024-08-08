package nodes

import Parser
import Token
import TokenType
import org.example.nodes.Node
import org.example.visitors.ExpressionVisitor
import types.BindingPowers

interface Led {

    fun parse(parser: Parser, token: Token, left: Node): Led
}