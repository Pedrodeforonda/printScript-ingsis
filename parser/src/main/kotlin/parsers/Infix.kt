package parsers

import main.Parser
import main.Token
import nodes.Node

interface Infix {
    fun parse(parser: Parser, left: Node, token: Token): Node
    fun getPrecedence(): Int
}
