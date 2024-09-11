package main

import nodes.Node

interface Prefix {
    fun parse(parser: Parser, token: Token): Node
}
