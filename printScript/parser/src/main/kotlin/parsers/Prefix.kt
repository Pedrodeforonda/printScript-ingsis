package parsers

import Parser
import Token
import org.example.nodes.Node

interface Prefix {
    fun parse(parser: Parser, token: Token): Node
}