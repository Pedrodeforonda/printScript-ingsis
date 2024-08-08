package org.example.visitors

import org.example.nodes.*

class EvalVisitor(private var variableMap: MutableMap<String, Any>): ExpressionVisitor {
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
        TODO("Not yet implemented")
    }

    override fun visitUnaryExp(expression: UnaryNode): Any {
        TODO("Not yet implemented")
    }

    override fun visitAssignment(expression: Assignment): Any {
        val (name, type) = expression.getDeclaration().accept(this) as Pair<*, *>
        if (type == "NUMBER_TYPE") {
            val value: Int = expression.getValue().accept(this) as Int
            variableMap[name as String] = value
        }

        if (type == "STRING_TYPE") {
            val value: String = expression.getValue().accept(this) as String
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