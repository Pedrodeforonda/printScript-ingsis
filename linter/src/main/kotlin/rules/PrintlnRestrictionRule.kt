package rules

import dataObjects.LinterResult
import dataObjects.StringResult
import nodes.CallNode
import nodes.Node
import visitors.GetStringVisitor

class PrintlnRestrictionRule(private val insideResult: LinterResult) : LinterRule {
    override fun lintCode(node: Node): LinterResult {
        val nodeInsidePrintln = (node as CallNode).getArguments().first()
        val nodeInsidePrintlnType: StringResult = nodeInsidePrintln.accept(GetStringVisitor()) as StringResult
        if (nodeInsidePrintlnType.value != "Identifier" && nodeInsidePrintlnType.value != "Literal" &&
            nodeInsidePrintlnType.value != "ReadInput" && nodeInsidePrintlnType.value != "ReadEnv"
        ) {
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
