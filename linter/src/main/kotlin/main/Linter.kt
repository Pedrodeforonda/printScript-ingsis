package main.kotlin.main

import nodes.Node

class Linter {

    fun lint(astNodes: Sequence<Node>, config: LinterConfig): Sequence<Any> = sequence {
        for (node in astNodes) {
            val result = node.accept(LinterVisitor(config)) as LinterResult
            if (result.hasError()) {
                yield(result.getMessage())
            }
        }
    }
}
