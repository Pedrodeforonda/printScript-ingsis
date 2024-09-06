package rules

import main.kotlin.main.LinterResult
import main.kotlin.rules.LinterRule
import nodes.Node
import nodes.ReadInput
import utils.GetStringVisitor

class ReadInputRestrictionRule(private val argumentResult: LinterResult) : LinterRule {
    override fun lintCode(node: Node): LinterResult {
        val nodeInsideReadInput = (node as ReadInput).getArgument()
        val nodeInsideReadInputType = nodeInsideReadInput.accept(GetStringVisitor())
        if (nodeInsideReadInputType != "Identifier" && nodeInsideReadInputType != "Literal") {
            val errorMessage = "Invalid readInput in line ${node.getPos().getLine()}" +
                " column ${node.getPos().getColumn()}"
            return checkErrors(errorMessage)
        }
        return LinterResult(argumentResult.getMessage(), argumentResult.hasError())
    }

    private fun checkErrors(errorMessage: String): LinterResult {
        if (argumentResult.hasError()) {
            return LinterResult(errorMessage + "\n" + argumentResult.getMessage(), true)
        }
        return LinterResult(errorMessage, true)
    }
}
