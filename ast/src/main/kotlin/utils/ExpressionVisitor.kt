package utils

import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal
import nodes.ReadInput
import nodes.ReadEnv
import nodes.IfNode

interface ExpressionVisitor {
    fun visitReadEnv(expression: ReadEnv): Result
    fun visitIf(expression: IfNode): Result
    fun visitDeclaration(expression: Declaration): Result
    fun visitLiteral(expression: Literal): Result
    fun visitBinaryExp(expression: BinaryNode): Result
    fun visitAssignment(expression: Assignation): Result
    fun visitCallExp(expression: CallNode): Result
    fun visitIdentifier(expression: Identifier): Result
    fun visitReadInput(expression: ReadInput): Result
}
