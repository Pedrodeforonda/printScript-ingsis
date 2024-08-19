package parsers

import Parser
import Token
import nodes.Node

interface Prefix {
    fun parse(parser: Parser, token: Token): Node
}
