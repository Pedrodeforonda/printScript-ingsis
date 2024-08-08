package nodes

import Parser

interface Stmt {
    fun parse(parser: Parser): Stmt
}