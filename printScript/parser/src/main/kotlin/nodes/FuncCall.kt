package nodes

import Parser

interface FuncCall {
    fun parse(parser: Parser): FuncCall
}