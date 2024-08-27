package org.example

import ExpressionVisitor
import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal

class EvalVisitor(private var variableMap: MutableMap<Pair<String, String>, Any>) : ExpressionVisitor {

    override fun visitDeclaration(expression: Declaration): Any {
        return Pair(expression.getName(), expression.getType())
    }

    override fun visitLiteral(expression: Literal): Any {
        return expression.getValue()
    }

    override fun visitBinaryExp(expression: BinaryNode): Any {
        val left = expression.getLeft().accept(this)
        val right = expression.getRight().accept(this)
        val operator = expression.getOperator()

        if (left is Int && right is Int) {
            return when (operator.getType()) {
                TokenType.PLUS -> left + right
                TokenType.MINUS -> left - right
                TokenType.ASTERISK -> left * right
                TokenType.SLASH -> left / right
                else -> throw Exception("Invalid operator")
            }
        }
        if (left is String && right is String && operator.getType() == TokenType.PLUS) {
            return left + right
        }
        if (left is String && right is Int && operator.getType() == TokenType.PLUS) {
            return left + right
        }
        if (left is Int && right is String && operator.getType() == TokenType.PLUS) {
            return left.toString() + right
        }

        throw Exception("Invalid operation")
    }

    override fun visitAssignment(expression: Assignation): Any {
        val (name, type) = if (expression.getDeclaration() is Identifier) {
            val identifierName = (expression.getDeclaration() as Identifier).getName()
            val identifierType = variableMap.keys.find { it.first == identifierName }?.second
            identifierName to identifierType
        } else {
            expression.getDeclaration().accept(this) as Pair<*, *>
        }

        val value = expression.getValue().accept(this)

        if (type == "number" && value is Number) {
            variableMap[Pair(name as String, type as String)] = value
        }
        if (type == "string" && value is String) {
            variableMap[Pair(name as String, type as String)] = value
        }

        return Exception("Invalid type")
    }

    override fun visitCallExp(expression: CallNode): Any {
        if (expression.getFunc() == "println") {
            val arg = expression.getArguments()
            if (arg.size != 1) {
                throw Exception("Invalid number of arguments")
            }
            val value = arg[0].accept(this)
            println(value)
        }
        return Unit
    }

    override fun visitIdentifier(expression: Identifier): Any {
        for ((key, value) in variableMap) {
            if (key.first == expression.getName()) {
                return value
            }
        }
        throw Exception("Variable not found")
    }
}
