package parsers

import Parser
import Token
import org.example.nodes.Node

interface Infix {
    fun parse(parser: Parser, left: Node, token: Token): Node
    fun getPrecedence(): Int
}