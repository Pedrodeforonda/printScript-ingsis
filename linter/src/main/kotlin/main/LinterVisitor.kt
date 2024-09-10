package main

import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.IfNode
import nodes.Literal
import nodes.ReadEnv
import nodes.ReadInput
import rules.IdentifierFormatRule
import rules.PrintlnRestrictionRule
import rules.ReadInputRestrictionRule
import utils.ExpressionVisitor
import utils.LinterResult

class LinterVisitor(private val config: LinterConfig) : ExpressionVisitor {
    override fun visitReadEnv(expression: ReadEnv): LinterResult {
        TODO("Not yet implemented")
    }

    override fun visitIf(expression: IfNode): LinterResult {
        TODO("Not yet implemented")
    }

    override fun visitDeclaration(expression: Declaration): LinterResult {
        val identifier = Identifier(
            expression.getName(),
            Position(expression.getPos().getLine(), expression.getPos().getColumn() + 3),
        )
        return identifier.accept(this) as LinterResult
    }

    override fun visitLiteral(expression: Literal): LinterResult {
        return LinterResult(null, false)
    }

    override fun visitBinaryExp(expression: BinaryNode): LinterResult {
        val leftResult = expression.getLeft().accept(this) as LinterResult
        val rightResult = expression.getRight().accept(this) as LinterResult
        return checkErrors(leftResult, rightResult)
    }

    override fun visitAssignment(expression: Assignation): LinterResult {
        val leftResult = expression.getDeclaration().accept(this) as LinterResult
        val rightResult = expression.getValue().accept(this) as LinterResult
        return checkErrors(leftResult, rightResult)
    }

    override fun visitCallExp(expression: CallNode): LinterResult {
        val insideResult = expression.getArguments().first().accept(this) as LinterResult
        if (config.restrictPrintln) {
            if (expression.getFunc() == "println") {
                return PrintlnRestrictionRule(insideResult).lintCode(expression)
            }
        }
        return LinterResult(insideResult.getMessage(), insideResult.hasError())
    }

    override fun visitIdentifier(expression: Identifier): LinterResult {
        val identifierFormat = IdentifierFormats().formats.find { it.getFormat() == config.identifier_format }
        if (identifierFormat != null) {
            return IdentifierFormatRule(identifierFormat).lintCode(expression)
        }
        return LinterResult(null, false)
    }

    override fun visitReadInput(expression: ReadInput): LinterResult {
        val argumentResult = expression.getArgument().accept(this) as LinterResult
        if (config.restrictReadInput) {
            return ReadInputRestrictionRule(argumentResult).lintCode(expression)
        }
        return LinterResult(argumentResult.getMessage(), argumentResult.hasError())
    }

    private fun checkErrors(left: LinterResult, right: LinterResult): LinterResult {
        return when {
            left.hasError() && !right.hasError() -> LinterResult(left.getMessage(), true)
            right.hasError() && !left.hasError() -> LinterResult(right.getMessage(), true)
            left.hasError() && right.hasError() -> LinterResult(left.getMessage() + "\n" + right.getMessage(), true)
            else -> LinterResult(null, false)
        }
    }
}
