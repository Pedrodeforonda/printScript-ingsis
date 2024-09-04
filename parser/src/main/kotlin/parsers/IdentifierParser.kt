package parsers

import main.Parser
import main.Token
import nodes.Identifier
import nodes.Node

class IdentifierParser : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        parser.consume()
        return Identifier(token.getText(), token.getPosition())
    }
}
