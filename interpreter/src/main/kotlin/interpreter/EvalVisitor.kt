package interpreter

import dataObjects.DeclarationResult
import dataObjects.IdentifierResult
import dataObjects.InterpreterException
import dataObjects.LiteralResult
import dataObjects.ReadResult
import dataObjects.Result
import dataObjects.SuccessResult
import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.IfNode
import nodes.Literal
import nodes.LiteralValue
import nodes.Node
import nodes.ReadEnv
import nodes.ReadInput
import types.BinaryOperatorType
import types.DeclarationType
import utils.PrintlnCollector
import utils.StringInputProvider
import visitors.ExpressionVisitor

class EvalVisitor(
    private var variableMap: MutableMap<String, Triple<Any, String, Boolean>>,
    private val printlnCollector: PrintlnCollector,
    private val inputValues: StringInputProvider,
    private val envVariables: Map<String, String>,
    private val canPrint: Boolean = true,
) : ExpressionVisitor {
    override fun visitReadEnv(expression: Node): Result {
        val readEnvExp = isValidType(expression, ReadEnv::class.java)

        val name = readEnvExp.getName()
        if (envVariables.containsKey(name)) {
            return ReadResult(envVariables[name]!!)
        }
        throw InterpreterException("Variable not found in environment")
    }

    override fun visitIf(expression: Node): Result {
        val ifExpression = isValidType(expression, IfNode::class.java)

        val condition = ifExpression.getCondition().accept(this)
        val thenBlock = ifExpression.getThenBlock()
        val elseBlock = ifExpression.getElseBlock()

        if (condition is LiteralResult) {
            executeIf(condition.value, thenBlock, elseBlock)
        }
        if (condition is IdentifierResult) {
            executeIf(condition.value, thenBlock, elseBlock)
        }

        return SuccessResult("If executed")
    }

    private fun executeIf(condition: Any, thenBlock: List<Node>?, elseBlock: List<Node>?) {
        if (condition !is Boolean) {
            throw InterpreterException("Invalid condition")
        }
        if (condition) {
            if (thenBlock != null) {
                for (node in thenBlock) {
                    node.accept(this)
                }
            }
        } else if (elseBlock != null) {
            for (node in elseBlock) {
                node.accept(this)
            }
        }
    }

    override fun visitDeclaration(expression: Node): Result {
        val declarationExpression = isValidType(expression, Declaration::class.java)

        val declarationKeyWord = declarationExpression.getDeclarationKeyWord()
        if (declarationKeyWord == DeclarationType.LET_KEYWORD) {
            variableMap[declarationExpression.getName()] = Triple(Unit, declarationExpression.getType(), true)
            return DeclarationResult(declarationExpression.getName())
        } else {
            variableMap[declarationExpression.getName()] = Triple(Unit, declarationExpression.getType(), false)
            return DeclarationResult(declarationExpression.getName())
        }
    }

    override fun visitLiteral(expression: Node): Result {
        val literalExpression = isValidType(expression, Literal::class.java)
        return when (literalExpression.getValue()) {
            is LiteralValue.StringValue -> LiteralResult(
                (literalExpression.getValue() as LiteralValue.StringValue).value,
            )
            is LiteralValue.NumberValue -> LiteralResult(
                (literalExpression.getValue() as LiteralValue.NumberValue).value,
            )
            is LiteralValue.BooleanValue -> LiteralResult(
                (literalExpression.getValue() as LiteralValue.BooleanValue).value,
            )
        }
    }

    override fun visitBinaryExp(expression: Node): Result {
        val binaryExp = isValidType(expression, BinaryNode::class.java)

        val operator = binaryExp.getOperator()
        val leftResult = binaryExp.getLeft().accept(this)
        val rightResult = binaryExp.getRight().accept(this)

        val left = if (leftResult is IdentifierResult) {
            leftResult.value
        } else (leftResult as LiteralResult).value

        val right = if (rightResult is IdentifierResult) {
            rightResult.value
        } else (rightResult as LiteralResult).value

        if (left is Number && right is Number) {
            return when (operator) {
                BinaryOperatorType.PLUS -> LiteralResult(
                    if (left is Double || right is Double) {
                        left.toDouble() + right.toDouble()
                    } else left.toInt() + right.toInt(),
                )

                BinaryOperatorType.MINUS -> LiteralResult(
                    if (left is Double || right is Double) {
                        left.toDouble() - right.toDouble()
                    } else left.toInt() - right.toInt(),
                )

                BinaryOperatorType.ASTERISK -> LiteralResult(
                    if (left is Double || right is Double) {
                        left.toDouble() * right.toDouble()
                    } else left.toInt() * right.toInt(),
                )

                BinaryOperatorType.SLASH -> LiteralResult(
                    if (left is Double || right is Double) {
                        left.toDouble() / right.toDouble()
                    } else left.toInt() / right.toInt(),
                )

                else -> throw InterpreterException("Invalid operator")
            }
        }
        if (left is String && right is String && operator.isSum()) {
            return LiteralResult(left + right)
        }
        if (left is String && right is Number && operator.isSum()) {
            return LiteralResult(left + right)
        }
        if (left is Number && right is String && operator.isSum()) {
            return LiteralResult(left.toString() + right)
        }

        throw InterpreterException("Invalid operation")
    }

    override fun visitAssignment(expression: Node): Result {
        val assignationExp = isValidType(expression, Assignation::class.java)

        val left = assignationExp.getDeclaration().accept(this) as Result
        val right = assignationExp.getValue().accept(this) as Result

        val name = if (left is DeclarationResult) {
            left.name
        } else (left as IdentifierResult).name

        val type = variableMap[name]!!.second
        val isMutable = variableMap[name]!!.third

        if (left !is DeclarationResult && !isMutable) {
            throw InterpreterException("Variable $name is not mutable")
        }

        if (right is ReadResult) {
            if (type == "number") {
                variableMap[name] = Triple(castToNumber(right.value), type, isMutable)
                return SuccessResult("variable assigned")
            }
            if (type == "boolean") {
                variableMap[name] =
                    when (right.value) {
                        "true" -> Triple(true, type, isMutable)
                        "false" -> Triple(false, type, isMutable)
                        else -> throw InterpreterException("Invalid boolean format")
                    }
                return SuccessResult("variable assigned")
            }
            variableMap[name] = Triple(right.value, type, isMutable)
            return SuccessResult("variable assigned")
        }

        if ((type == "number" && (right as LiteralResult).value is Number) ||
            (type == "string" && (right as LiteralResult).value is String) ||
            (type == "boolean" && (right as LiteralResult).value is Boolean)
        ) {
            variableMap[name] = Triple(right.value, type, isMutable)
            return SuccessResult("variable assigned")
        }

        throw InterpreterException(
            "Invalid type: expected $type, but got ${getType((right as LiteralResult).value)} on variable $name," +
                " at line ${assignationExp.getPos().getLine()} column ${assignationExp.getPos().getColumn()}",
        )
    }

    override fun visitCallExp(expression: Node): Result {
        val callExp = isValidType(expression, CallNode::class.java)

        if (callExp.getFunc() == "println") {
            val arg = callExp.getArguments()
            if (arg.size != 1) {
                throw InterpreterException("Invalid number of arguments")
            }
            val result = arg[0].accept(this)
            if (result is IdentifierResult) {
                if (canPrint) println(result.value.toString())
                printlnCollector.addPrint(result.value.toString())
                return SuccessResult("Printed")
            }
            if (result is ReadResult) {
                if (canPrint) println(result.value)
                printlnCollector.addPrint(result.value)
                return SuccessResult("Printed")
            }
            if (canPrint) println((result as LiteralResult).value.toString())
            printlnCollector.addPrint((result as LiteralResult).value.toString())
            return SuccessResult("Printed")
        }
        throw InterpreterException("Invalid function")
    }

    override fun visitIdentifier(expression: Node): Result {
        val identifierExp = isValidType(expression, Identifier::class.java)

        val name = identifierExp.getName()
        if (variableMap.containsKey(name)) {
            return IdentifierResult(name, variableMap[name]!!.first)
        }
        throw InterpreterException("Variable not found")
    }

    override fun visitReadInput(expression: Node): Result {
        val readInputExp = isValidType(expression, ReadInput::class.java)

        val arg = readInputExp.getArgument().accept(this)
        val message = if (arg is IdentifierResult && arg.value is String) {
            arg.value as String
        } else if (arg is LiteralResult && arg.value is String) {
            arg.value as String
        } else {
            throw InterpreterException("ReadInput argument must be a string")
        }

        printlnCollector.addPrint(message)
        val input = inputValues.input(message)
        if (canPrint) println(input)

        return ReadResult(input)
    }

    private fun getType(value: Any): String {
        return when (value) {
            is Number -> "number"
            is String -> "string"
            else -> "unknown"
        }
    }

    private fun castToNumber(value: String): Number {
        return value.toIntOrNull() ?: value.toDoubleOrNull() ?: throw InterpreterException("Invalid number format")
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
