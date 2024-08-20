package org.example

import ExpressionVisitor
import nodes.BinaryNode
import nodes.Declaration
import nodes.GroupingNode
import nodes.Literal
import nodes.UnaryNode

class EvalVisitor(private var variableMap: MutableMap<String, Any>) : ExpressionVisitor {
    override fun visitDeclaration(expression: Declaration): Any {
        variableMap[expression.getName()] = expression.getType()
        return Pair(expression.getName(), expression.getType())
    }

    override fun visitLiteral(expression: Literal): Any {
        return expression.getValue()
    }

    override fun visitGroupingExp(expression: GroupingNode): Any {
        return expression.getNode().accept(this)
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

    override fun visitUnaryExp(expression: UnaryNode): Any {
        TODO("Not yet implemented")
    }

    override fun visitAssignment(expression: Assignation): Any {
        val (name, type) = expression.getDeclaration().accept(this) as Pair<*, *>
        if (type == "NUMBER_TYPE" || type == "STRING_TYPE") {
            val value = expression.getValue().accept(this)
            variableMap[name as String] = value
        }

        return Unit
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
        return variableMap[expression.getValue()] ?: throw Exception("Variable not found")
    }
}
