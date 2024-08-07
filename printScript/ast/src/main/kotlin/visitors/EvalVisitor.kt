package org.example.visitors

import org.example.nodes.*

class EvalVisitor: ExpressionVisitor {
    override fun visitDeclaration(expression: Declaration): Any {
        return (expression.getName() to expression.getType())
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
        if (type == "number") {
            val value: Int = expression.getValue().accept(this) as Int
            return (name to value)
        }

        if (type == "string") {
            val value: String = expression.getValue().accept(this) as String
            return (name to value)
        }
    }

    override fun visitCallExp(expression: CallNode): Any {
        if (expression.getFunc() == "println") {
            val arg = expression.getArguments()
            println(arg)
        }
        return Unit
    }
}