import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal

interface ExpressionVisitor {
    fun visitDeclaration(expression: Declaration): Any
    fun visitLiteral(expression: Literal): Any
    fun visitBinaryExp(expression: BinaryNode): Any
    fun visitAssignment(expression: Assignation): Any
    fun visitCallExp(expression: CallNode): Any
    fun visitIdentifier(expression: Identifier): Any
}
