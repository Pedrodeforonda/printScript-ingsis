package parsers

import main.Parser
import main.Token
import nodes.Node
import nodes.PrefixExpression

class PrefixOperatorParser : Prefix {

    override fun parse(parser: Parser, token: Token): Node {
        val right: Node = parser.parseExpression()
        return PrefixExpression(token.getType(), right)
    }
}
