package parsers

import Parser
import Token
import nodes.PrefixExpression
import org.example.nodes.Node

class PrefixOperatorParser : Prefix {

    override fun parse(parser: Parser, token: Token): Node {
        val right: Node = parser.parseExpression()
        return PrefixExpression(token.getType(), right)
    }

}