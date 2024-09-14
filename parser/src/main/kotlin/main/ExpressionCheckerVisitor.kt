package main

import dataObjects.CheckAstResult
import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.IfNode
import nodes.Literal
import nodes.Node
import nodes.ReadEnv
import nodes.ReadInput
import visitors.ExpressionVisitor

class ExpressionCheckerVisitor(private val variableTypeMap: MutableMap<String, String>) : ExpressionVisitor {
    override fun visitReadEnv(expression: Node): CheckAstResult {
        isValidType(expression, ReadEnv::class.java)
        return CheckAstResult(true, "", "ReadEnv")
    }

    override fun visitIf(expression: Node): CheckAstResult {
        val ifExpression = isValidType(expression, IfNode::class.java)

        val condition = ifExpression.getCondition()
        val body = ifExpression.getThenBlock()
        val elseBody = ifExpression.getElseBlock()
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
    override fun visitDeclaration(expression: Node): CheckAstResult {
        val declarationExpression = isValidType(expression, Declaration::class.java)

        val variableName = declarationExpression.getName()
        val variableType = declarationExpression.getType()
        variableTypeMap[variableName] = variableType
        return CheckAstResult(true, "", "Declaration")
    }

    override fun visitLiteral(expression: Node): CheckAstResult {
        val literalExpression = isValidType(expression, Literal::class.java)
        if (literalExpression.isString()) {
            return CheckAstResult(true, "", "string")
        }
        if (literalExpression.isNumber()) {
            return CheckAstResult(true, "", "number")
        }
        if (literalExpression.isBoolean()) {
            return CheckAstResult(true, "", "boolean")
        }
        return CheckAstResult(false, "Invalid literal", "Literal")
    }

    override fun visitBinaryExp(expression: Node): CheckAstResult {
        val binaryExpression = isValidType(expression, BinaryNode::class.java)

        val left = binaryExpression.getLeft()
        val right = binaryExpression.getRight()
        val leftResult = left.accept(this) as CheckAstResult
        val rightResult = right.accept(this) as CheckAstResult
        val operator = binaryExpression.getOperator()
        if (!operator.isSum()) {
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

    override fun visitAssignment(expression: Node): CheckAstResult {
        val assignationExpression = isValidType(expression, Assignation::class.java)

        if (assignationExpression.getDeclaration() is Identifier) {
            return CheckAstResult(true, "", "Identifier")
        }
        val declaration = assignationExpression.getDeclaration() as Declaration
        val isError: CheckAstResult = declaration.accept(this) as CheckAstResult
        if (!isError.isPass()) {
            return CheckAstResult(false, isError.getMessage(), "Declaration")
        }
        Pair(declaration.getName(), declaration.getType())
        val value = assignationExpression.getValue()
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
                    " line ${assignationExpression.getPos().getLine()}: " +
                    "column ${assignationExpression.getPos().getColumn()}",
                "Assignation",
            )
        }
        return CheckAstResult(true, "", valueResult.getResultType())
    }

    override fun visitCallExp(expression: Node): CheckAstResult {
        val callExpression = isValidType(expression, CallNode::class.java)

        val functionName = callExpression.getFunc()
        val arguments = callExpression.getArguments()
        if (functionName != "println") {
            return CheckAstResult(false, "Function $functionName is not defined", "Print")
        }
        if (arguments.size > 1) {
            return CheckAstResult(false, "Invalid number of arguments", "Print")
        }
        return CheckAstResult(true, "", "Print")
    }

    override fun visitIdentifier(expression: Node): CheckAstResult {
        isValidType(expression, Identifier::class.java)
        return CheckAstResult(true, "", "Identifier")
    }

    override fun visitReadInput(expression: Node): CheckAstResult {
        val readInputExpression = isValidType(expression, ReadInput::class.java)

        when (val message = readInputExpression.getArgument()) {
            is Literal -> {
                if (!message.isString()) {
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

    private fun <T : Node> isValidType(expression: Node, expectedType: Class<T>): T {
        if (!expectedType.isInstance(expression)) {
            throw Exception(
                "Invalid type: expected ${expectedType.simpleName}, but got ${expression::class.simpleName}",
            )
        }
        return expectedType.cast(expression)
    }
}
