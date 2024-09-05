package main.kotlin.main

import utils.ParsingResult

class Linter {

    fun lint(parsingResults: Sequence<ParsingResult>, config: LinterConfig): Sequence<LinterResult> = sequence {
        for (parsingResult in parsingResults) {
            if (parsingResult.hasError()) {
                yieldAll(getOnlyParserErrors(parsingResults))
                break
            }

            if (!parsingResult.hasError()) {
                val result = parsingResult.getAst().accept(LinterVisitor(config)) as LinterResult
                if (result.hasError()) {
                    yield(result)
                }
            }
        }
    }

    private fun getOnlyParserErrors(parsingResults: Sequence<ParsingResult>): Sequence<LinterResult> = sequence {
        for (parsingResult in parsingResults) {
            if (parsingResult.hasError()) {
                yield(LinterResult(parsingResult.getError().message!!, true))
            }
        }
    }
}
