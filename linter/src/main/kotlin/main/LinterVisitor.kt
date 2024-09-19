package main

import dataObjects.LinterResult
import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.IfNode
import nodes.Literal
import nodes.Node
import nodes.Position
import nodes.ReadEnv
import nodes.ReadInput
import rules.IdentifierFormatRule
import rules.PrintlnRestrictionRule
import rules.ReadInputRestrictionRule
import visitors.ExpressionVisitor

class LinterVisitor(private val config: LinterConfig) : ExpressionVisitor {
    override fun visitReadEnv(expression: Node): LinterResult {
        isValidType(expression, ReadEnv::class.java)
        return LinterResult(null, false)
    }

    override fun visitIf(expression: Node): LinterResult {
        val ifExpression = isValidType(expression, IfNode::class.java)

        val conditionResult = ifExpression.getCondition().accept(this) as LinterResult
        val thenBlockResult = ifExpression.getThenBlock()?.map { it.accept(this) as LinterResult }
        val elseBlockResult = ifExpression.getElseBlock()?.map { it.accept(this) as LinterResult }
        val errorMessages = mutableListOf<String>()

        if (conditionResult.hasError()) {
            errorMessages.add(conditionResult.getMessage())
        }
        if (thenBlockResult != null && thenBlockResult.any { it.hasError() }) {
            errorMessages.addAll(thenBlockResult.filter { it.hasError() }.map { it.getMessage() })
        }
        if (elseBlockResult != null && elseBlockResult.any { it.hasError() }) {
            errorMessages.addAll(elseBlockResult.filter { it.hasError() }.map { it.getMessage() })
        }

        return if (errorMessages.isNotEmpty()) {
            LinterResult(errorMessages.joinToString("\n"), true)
        } else {
            LinterResult(null, false)
        }
    }

    override fun visitDeclaration(expression: Node): LinterResult {
        val declarationExpression = isValidType(expression, Declaration::class.java)

        val identifier = Identifier(
            declarationExpression.getName(),
            Position(declarationExpression.getPos().getLine(), declarationExpression.getPos().getColumn() + 3),
        )
        return identifier.accept(this) as LinterResult
    }

    override fun visitLiteral(expression: Node): LinterResult {
        isValidType(expression, Literal::class.java)

        return LinterResult(null, false)
    }

    override fun visitBinaryExp(expression: Node): LinterResult {
        val binaryExp = isValidType(expression, BinaryNode::class.java)

        val leftResult = binaryExp.getLeft().accept(this) as LinterResult
        val rightResult = binaryExp.getRight().accept(this) as LinterResult
        return checkErrors(leftResult, rightResult)
    }

    override fun visitAssignment(expression: Node): LinterResult {
        val assignmentExp = isValidType(expression, Assignation::class.java)

        val leftResult = assignmentExp.getDeclaration().accept(this) as LinterResult
        val rightResult = assignmentExp.getValue().accept(this) as LinterResult
        return checkErrors(leftResult, rightResult)
    }

    override fun visitCallExp(expression: Node): LinterResult {
        val callExp = isValidType(expression, CallNode::class.java)

        val insideResult = callExp.getArguments().first().accept(this) as LinterResult
        if (config.restrictPrintln) {
            if (callExp.getFunc() == "println") {
                return PrintlnRestrictionRule(insideResult).lintCode(callExp)
            }
        }
        return LinterResult(insideResult.getMessage(), insideResult.hasError())
    }

    override fun visitIdentifier(expression: Node): LinterResult {
        val identifierExp = isValidType(expression, Identifier::class.java)

        val identifierFormat = IdentifierFormats().formats.find { it.getFormat() == config.identifier_format }
        if (identifierFormat != null) {
            return IdentifierFormatRule(identifierFormat).lintCode(identifierExp)
        }
        return LinterResult(null, false)
    }

    override fun visitReadInput(expression: Node): LinterResult {
        val readInputExp = isValidType(expression, ReadInput::class.java)

        val argumentResult = readInputExp.getArgument().accept(this) as LinterResult
        if (config.restrictReadInput) {
            return ReadInputRestrictionRule(argumentResult).lintCode(readInputExp)
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

    private fun <T : Node> isValidType(expression: Node, expectedType: Class<T>): T {
        if (!expectedType.isInstance(expression)) {
            throw Exception(
                "Invalid type: expected ${expectedType.simpleName}, but got ${expression::class.simpleName}",
            )
        }
        return expectedType.cast(expression)
    }
}
