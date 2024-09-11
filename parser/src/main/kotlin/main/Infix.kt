package main

import nodes.Node

interface Infix {
    fun parse(parser: Parser, left: Node, token: Token): Node
    fun getPrecedence(): Int
}
