package dataObjects

import nodes.Node

class ParsingResult(private val ast: Node?, private val error: Exception?) {
    fun hasError(): Boolean {
        return error != null
    }

    fun getError(): Exception {
        return error!!
    }

    fun getAst(): Node {
        return ast!!
    }
}
