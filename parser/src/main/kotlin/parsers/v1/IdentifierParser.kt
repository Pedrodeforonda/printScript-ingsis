package parsers.v1

import main.Parser
import main.Prefix
import main.Token
import nodes.Identifier
import nodes.Node

class IdentifierParser : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        parser.consume()
        return Identifier(token.getText(), parser.adaptPos(token.getPosition()))
    }
}
