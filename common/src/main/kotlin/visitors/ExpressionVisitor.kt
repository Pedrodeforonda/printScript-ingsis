package visitors

import dataObjects.Result
import nodes.Node

interface ExpressionVisitor {
    fun visitReadEnv(expression: Node): Result
    fun visitIf(expression: Node): Result
    fun visitDeclaration(expression: Node): Result
    fun visitLiteral(expression: Node): Result
    fun visitBinaryExp(expression: Node): Result
    fun visitAssignment(expression: Node): Result
    fun visitCallExp(expression: Node): Result
    fun visitIdentifier(expression: Node): Result
    fun visitReadInput(expression: Node): Result
}
