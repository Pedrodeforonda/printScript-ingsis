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
import utils.CheckAstResult
import utils.ExpressionVisitor
import utils.Result

class ExpressionCheckerVisitor(private val variableTypeMap: MutableMap<String, String>) : ExpressionVisitor {
    override fun visitReadEnv(expression: ReadEnv): CheckAstResult {
        return CheckAstResult(true, "", "ReadEnv")
    }

    override fun visitIf(expression: IfNode): CheckAstResult {
        val condition = expression.getCondition()
        val body = expression.getThenBlock()
        val elseBody = expression.getElseBlock()
        val conditionResult = condition.accept(this) as CheckAstResult
        if (!conditionResult.isPass()) {
            return CheckAstResult(
                false,
                conditionResult.getMessage(),
                conditionResult.getResultType(),
            )
        }
        if (conditionResult.getResultType() != "boolean" && conditionResult.getResultType() != "Identifier") {
            return CheckAstResult(
                false,
                "Invalid type: expected boolean on condition, but got ${conditionResult.getResultType()}",
                "If",
            )
        }
        if (body != null) {
            for (node in body) {
                val result = node.accept(this) as CheckAstResult
                if (!result.isPass()) {
                    return CheckAstResult(
                        false,
                        result.getMessage(),
                        result.getResultType(),
                    )
                }
            }
        }
        if (elseBody != null) {
            for (node in elseBody) {
                val result = node.accept(this) as CheckAstResult
                if (!result.isPass()) {
                    return CheckAstResult(
                        false,
                        result.getMessage(),
                        result.getResultType(),
                    )
                }
            }
        }
        return CheckAstResult(true, "", "If")
    }
    override fun visitDeclaration(expression: Declaration): Result {
        val variableName = expression.getName()
        val variableType = expression.getType()
        variableTypeMap[variableName] = variableType
        return CheckAstResult(true, "", "Declaration")
    }

    override fun visitLiteral(expression: Literal): CheckAstResult {
        val value = expression.getValue()
        if (value is String) {
            return CheckAstResult(true, "", "string")
        }
        if (value is Int || value is Double) {
            return CheckAstResult(true, "", "number")
        }
        if (value is Boolean) {
            return CheckAstResult(true, "", "boolean")
        }
        return CheckAstResult(false, "Invalid literal", "Literal")
    }

    override fun visitBinaryExp(expression: BinaryNode): CheckAstResult {
        val left = expression.getLeft()
        val right = expression.getRight()
        val leftResult = left.accept(this) as CheckAstResult
        val rightResult = right.accept(this) as CheckAstResult
        val operator = expression.getOperator()
        if (operator.getType() != TokenType.PLUS) {
            if (leftResult.getResultType() != rightResult.getResultType() &&
                leftResult.getResultType() != "Identifier" && rightResult.getResultType() != "Identifier"
            ) {
                return CheckAstResult(false, "Binary operation not supported", "invalid")
            }
        }
        if (leftResult.getResultType() == "Boolean" || rightResult.getResultType() == "Boolean") {
            return CheckAstResult(
                false,
                "Binary operation not supported between booleans",
                "invalid",
            )
        }

        if (leftResult.getResultType() == "string" || rightResult.getResultType() == "string") {
            return CheckAstResult(true, "", "string")
        }

        return CheckAstResult(true, "", "number")
    }

    override fun visitAssignment(expression: Assignation): CheckAstResult {
        if (expression.getDeclaration() is Identifier) {
            return CheckAstResult(true, "", "Identifier")
        }
        val declaration = expression.getDeclaration() as Declaration
        val isError: CheckAstResult = declaration.accept(this) as CheckAstResult
        if (!isError.isPass()) {
            return CheckAstResult(false, isError.getMessage(), "Declaration")
        }
        Pair(declaration.getName(), declaration.getType())
        val value = expression.getValue()
        val type = declaration.getType()
        val name = declaration.getName()
        val valueResult = value.accept(this) as CheckAstResult
        if (!valueResult.isPass()) {
            return CheckAstResult(
                false,
                valueResult.getMessage(),
                valueResult.getResultType(),
            )
        }

        when (valueResult.getResultType()) {
            "Identifier" -> {
                return CheckAstResult(true, "", valueResult.getResultType())
            }
            "ReadInput" -> {
                return CheckAstResult(true, "", "ReadInput")
            }
            "ReadEnv" -> {
                return CheckAstResult(true, "", "ReadEnv")
            }
        }

        if (valueResult.getResultType() != type) {
            return CheckAstResult(
                false,
                "Invalid type: expected $type on variable $name," +
                    " but got ${valueResult.getResultType()} at" +
                    " line ${expression.getPos().getLine()}: column ${expression.getPos().getColumn()}",
                "Assignation",
            )
        }
        return CheckAstResult(true, "", valueResult.getResultType())
    }

    override fun visitCallExp(expression: CallNode): CheckAstResult {
        val functionName = expression.getFunc()
        val arguments = expression.getArguments()
        if (functionName != "println") {
            return CheckAstResult(false, "Function $functionName is not defined", "Print")
        }
        if (arguments.size > 1) {
            return CheckAstResult(false, "Invalid number of arguments", "Print")
        }
        return CheckAstResult(true, "", "Print")
    }

    override fun visitIdentifier(expression: Identifier): CheckAstResult {
        return CheckAstResult(true, "", "Identifier")
    }

    override fun visitReadInput(expression: ReadInput): CheckAstResult {
        when (val message = expression.getArgument()) {
            is Literal -> {
                if (message.getType() != TokenType.STRING_LITERAL) {
                    return CheckAstResult(false, "Expected STRING_LITERAL", "ReadInput")
                }
                return CheckAstResult(true, "", "ReadInput")
            }
            is Identifier -> {
                return CheckAstResult(true, "", "ReadInput")
            }
            is BinaryNode -> {
                val result = message.accept(this) as CheckAstResult
                if (!result.isPass()) {
                    return CheckAstResult(false, result.getMessage(), "ReadInput")
                }
                return CheckAstResult(true, "", "ReadInput")
            }
            else -> {
                return CheckAstResult(false, "Expected STRING_LITERAL", "ReadInput")
            }
        }
    }
}
