package nodes

import Parser
import Token
import TokenType
import org.example.nodes.Node
import org.example.visitors.ExpressionVisitor
import types.BindingPowers

interface Nud {

    fun parse(parser: Parser, token: Token): Nud


}