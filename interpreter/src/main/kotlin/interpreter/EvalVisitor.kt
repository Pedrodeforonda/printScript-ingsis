package org.example.interpreter

import main.TokenType
import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal
import utils.DeclarationResult
import utils.ExpressionVisitor
import utils.IdentifierResult
import utils.InterpreterException
import utils.LiteralResult
import utils.Result
import utils.SuccessResult

class EvalVisitor(private var variableMap: MutableMap<Pair<String, String>, Any>) : ExpressionVisitor {

    override fun visitDeclaration(expression: Declaration): Any {
        return DeclarationResult(expression.getName(), expression.getType())
    }

    override fun visitLiteral(expression: Literal): Any {
        return LiteralResult(expression.getValue())
    }

    override fun visitBinaryExp(expression: BinaryNode): Any {
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
                TokenType.PLUS -> LiteralResult(left.toInt() + right.toInt())
                TokenType.MINUS -> LiteralResult(left.toInt() - right.toInt())
                TokenType.ASTERISK -> LiteralResult(left.toInt() * right.toInt())
                TokenType.SLASH -> LiteralResult(left.toInt() / right.toInt())
                else -> throw Exception("Invalid operator")
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

        return InterpreterException("Invalid operation")
    }

    override fun visitAssignment(expression: Assignation): Any {
        val left = expression.getDeclaration().accept(this) as Result
        val (name, type) = if (left is DeclarationResult) {
            Pair(left.name, left.type)
        } else (left as IdentifierResult).nameAndType
        val right = expression.getValue().accept(this) as LiteralResult

        if (type == "number" && right.value is Number) {
            variableMap[Pair(name, type)] = right.value
            return SuccessResult("Number type variable assigned")
        }
        if (type == "string" && right.value is String) {
            variableMap[Pair(name, type)] = right.value
            return SuccessResult("String type variable assigned")
        }

        throw InterpreterException(
            "Invalid type: expected $type, but got ${getType(right.value)} on variable $name," +
                " at line ${expression.getPos().getLine()} column ${expression.getPos().getColumn()}",
        )
    }

    override fun visitCallExp(expression: CallNode): Any {
        if (expression.getFunc() == "println") {
            val arg = expression.getArguments()
            if (arg.size != 1) {
                return InterpreterException("Invalid number of arguments")
            }
            val result = arg[0].accept(this)
            if (result is IdentifierResult) {
                println(result.value)
                return SuccessResult("Printed")
            }
            println((result as LiteralResult).value)
            return SuccessResult("Printed")
        }
        return InterpreterException("Invalid function")
    }

    override fun visitIdentifier(expression: Identifier): Any {
        for ((key, value) in variableMap) {
            if (key.first == expression.getName()) {
                return IdentifierResult(key, value)
            }
        }
        return InterpreterException("Variable not found")
    }

    private fun getType(value: Any): String {
        return when (value) {
            is Number -> "number"
            is String -> "string"
            else -> "unknown"
        }
    }
}
