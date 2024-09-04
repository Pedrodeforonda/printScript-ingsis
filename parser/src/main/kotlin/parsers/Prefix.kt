package parsers

import main.Parser
import main.Token
import nodes.Node

interface Prefix {
    fun parse(parser: Parser, token: Token): Node
}
