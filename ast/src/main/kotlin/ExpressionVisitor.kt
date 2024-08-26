import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal

interface ExpressionVisitor {
    fun visitDeclaration(expression: Declaration): Result
    fun visitLiteral(expression: Literal): Result
    fun visitBinaryExp(expression: BinaryNode): Result
    fun visitAssignment(expression: Assignation): Result
    fun visitCallExp(expression: CallNode): Result
    fun visitIdentifier(expression: Identifier): Result
}
