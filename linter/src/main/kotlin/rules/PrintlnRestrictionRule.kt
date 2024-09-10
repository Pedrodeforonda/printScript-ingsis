package rules

import main.kotlin.main.LinterResult
import main.kotlin.rules.LinterRule
import nodes.CallNode
import nodes.Node
import utils.GetStringVisitor

class PrintlnRestrictionRule(private val insideResult: LinterResult) : LinterRule {
    override fun lintCode(node: Node): LinterResult {
        val nodeInsidePrintln = (node as CallNode).getArguments().first()
        val nodeInsidePrintlnType = nodeInsidePrintln.accept(GetStringVisitor())
        if (nodeInsidePrintlnType != "Identifier" && nodeInsidePrintlnType != "Literal") {
            val errorMessage = "Invalid println in line ${node.getPos().getLine()}" +
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
