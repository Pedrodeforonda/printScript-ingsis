package org.example

import org.example.nodes.*
import org.example.ExpressionVisitor

class PrinterVisitor: ExpressionVisitor {
    override fun visitDeclaration(expression: Declaration): Any {
        return "Declaration"
    }

    override fun visitLiteral(expression: Literal): Any {
        return "Literal"
    }

    override fun visitGroupingExp(expression: GroupingNode): Any {
        return "GroupingExpression"
    }

    override fun visitBinaryExp(expression: BinaryNode): Any {
        return "BinaryExpression"
    }

    override fun visitUnaryExp(expression: UnaryNode): Any {
        return "UnaryExpression"
    }

    override fun visitAssignment(expression: Assignment): Any {
        return "Assignment"
    }

    override fun visitCallExp(expression: CallNode): Any {
        return "CallExpression"
    }

    override fun visitIdentifier(expression: Identifier): Any {
        return "Identifier"
    }

}