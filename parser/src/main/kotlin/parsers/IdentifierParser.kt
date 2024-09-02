package parsers

import Parser
import Token
import nodes.Identifier
import nodes.Node

class IdentifierParser : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        parser.consume()
        return Identifier(token.getCharArray(), token.getPosition())
    }
}
