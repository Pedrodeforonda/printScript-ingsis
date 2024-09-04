package main.kotlin.rules

import main.kotlin.LinterResult
import nodes.CallNode
import nodes.Identifier
import nodes.Literal
import nodes.Node

class PrintlnRestrictionRule(private val insideResult: LinterResult) : LinterRule {
    override fun lintCode(node: Node): LinterResult {
        val nodeInsidePrintln = (node as CallNode).getArguments().first()
        if (nodeInsidePrintln !is Identifier && nodeInsidePrintln !is Literal) {
            val errorMessage = "Invalid Println in line ${node.getPos().getLine()}" +
                " column ${node.getPos().getColumn()}"
            return checkErrors(errorMessage)
        }
        return LinterResult(insideResult.getMessage(), insideResult.hasError())
    }

    private fun checkErrors(errorMessage: String): LinterResult {
        if (insideResult.hasError()) {
            return LinterResult(errorMessage + "\n" + insideResult.getMessage(), true)
        }
        return LinterResult(errorMessage, true)
    }
}
