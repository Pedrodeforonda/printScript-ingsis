package main.kotlin.main

import utils.ParsingResult

class Linter {

    fun lint(astNodes: Sequence<ParsingResult>, config: LinterConfig): Sequence<Any> = sequence {
        for (node in astNodes) {
            if (!node.hasError()) {
                val result = node.getAst().accept(LinterVisitor(config)) as LinterResult
                if (result.hasError()) {
                    yield(result)
                }
            }
            if (node.hasError()) {
                yield(LinterResult(node.getError().message!!, true))
            }
        }
    }
}
