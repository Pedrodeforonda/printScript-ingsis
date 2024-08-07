package org.example.visitors

import org.example.nodes.*

class EvalVisitor: ExpressionVisitor {
    override fun visitDeclaration(expression: Declaration): Any {
        return expression.getName()
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
        return when (expression.getOperator()) {
            "+" -> left as Double + right as Double
            "-" -> left as Double - right as Double
             -> left as Double * right as Double
            TokenType.SLASH -> left as Double / right as Double
            else -> throw IllegalArgumentException("Invalid operator")
        }
    }

    override fun visitUnaryExp(expression: UnaryNode): Any {
        TODO("Not yet implemented")
    }

    override fun visitAssignment(expression: Assignment): Any {

    }
}