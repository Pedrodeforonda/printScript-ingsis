package interpreter

import main.TokenType
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
import utils.DeclarationKeyWord
import utils.DeclarationResult
import utils.ExpressionVisitor
import utils.IdentifierResult
import utils.InterpreterException
import utils.LiteralResult
import utils.PrintlnCollector
import utils.ReadResult
import utils.Result
import utils.StringInputProvider
import utils.SuccessResult

class EvalVisitor(
    private var variableMap: MutableMap<String, Triple<Any, String, Boolean>>,
    private val printlnCollector: PrintlnCollector,
    private val inputValues: StringInputProvider,
    private val envVariables: Map<String, String>,
    private val canPrint: Boolean = true,
) : ExpressionVisitor {
    override fun visitReadEnv(expression: ReadEnv): Result {
        val name = expression.getName()
        if (envVariables.containsKey(name)) {
            return ReadResult(envVariables[name]!!)
        }
        throw InterpreterException("Variable not found in environment")
    }

    override fun visitIf(expression: IfNode): Result {
        val condition = expression.getCondition().accept(this)
        val thenBlock = expression.getThenBlock()
        val elseBlock = expression.getElseBlock()

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

    override fun visitDeclaration(expression: Declaration): Result {
        val declarationKeyWord = expression.getDeclarationKeyWord()
        if (declarationKeyWord == DeclarationKeyWord.LET_KEYWORD) {
            variableMap[expression.getName()] = Triple(Unit, expression.getType(), true)
            return DeclarationResult(expression.getName())
        } else {
            variableMap[expression.getName()] = Triple(Unit, expression.getType(), false)
            return DeclarationResult(expression.getName())
        }
    }

    override fun visitLiteral(expression: Literal): Result {
        return LiteralResult(expression.getValue())
    }

    override fun visitBinaryExp(expression: BinaryNode): Result {
        val operator = expression.getOperator()
        val leftResult = expression.getLeft().accept(this)
        val rightResult = expression.getRight().accept(this)

        val left = if (leftResult is IdentifierResult) {
            leftResult.value
        } else (leftResult as LiteralResult).value

        val right = if (rightResult is IdentifierResult) {
            rightResult.value
        } else (rightResult as LiteralResult).value

        if (left is Number && right is Number) {
            return when (operator.getType()) {
                TokenType.PLUS -> LiteralResult(
                    if (left is Double || right is Double) {
                        left.toDouble() + right.toDouble()
                    } else left.toInt() + right.toInt(),
                )

                TokenType.MINUS -> LiteralResult(
                    if (left is Double || right is Double) {
                        left.toDouble() - right.toDouble()
                    } else left.toInt() - right.toInt(),
                )

                TokenType.ASTERISK -> LiteralResult(
                    if (left is Double || right is Double) {
                        left.toDouble() * right.toDouble()
                    } else left.toInt() * right.toInt(),
                )

                TokenType.SLASH -> LiteralResult(
                    if (left is Double || right is Double) {
                        left.toDouble() / right.toDouble()
                    } else left.toInt() / right.toInt(),
                )

                else -> throw InterpreterException("Invalid operator")
            }
        }
        if (left is String && right is String && operator.getType() == TokenType.PLUS) {
            return LiteralResult(left + right)
        }
        if (left is String && right is Number && operator.getType() == TokenType.PLUS) {
            return LiteralResult(left + right)
        }
        if (left is Number && right is String && operator.getType() == TokenType.PLUS) {
            return LiteralResult(left.toString() + right)
        }

        throw InterpreterException("Invalid operation")
    }

    override fun visitAssignment(expression: Assignation): Result {
        val left = expression.getDeclaration().accept(this) as Result
        val right = expression.getValue().accept(this) as Result

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
                " at line ${expression.getPos().getLine()} column ${expression.getPos().getColumn()}",
        )
    }

    override fun visitCallExp(expression: CallNode): Result {
        if (expression.getFunc() == "println") {
            val arg = expression.getArguments()
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
                if (canPrint) println((result as ReadResult).value)
                printlnCollector.addPrint(result.value)
                return SuccessResult("Printed")
            }
            if (canPrint) println((result as LiteralResult).value.toString())
            printlnCollector.addPrint((result as LiteralResult).value.toString())
            return SuccessResult("Printed")
        }
        throw InterpreterException("Invalid function")
    }

    override fun visitIdentifier(expression: Identifier): Result {
        val name = expression.getName()
        if (variableMap.containsKey(name)) {
            return IdentifierResult(name, variableMap[name]!!.first)
        }
        throw InterpreterException("Variable not found")
    }

    override fun visitReadInput(expression: ReadInput): Result {
        val arg = expression.getArgument().accept(this)
        val message = if (arg is IdentifierResult && arg.value is String) {
            arg.value as String
        } else if (arg is LiteralResult && arg.value is String) {
            arg.value as String
        } else {
            throw InterpreterException("ReadInput argument must be a string")
        }

        println(message)
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
}
