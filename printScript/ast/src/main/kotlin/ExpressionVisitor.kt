import nodes.*

interface ExpressionVisitor {
    fun visitDeclaration(expression: Declaration): Any
    fun visitLiteral(expression: Literal): Any
    fun visitGroupingExp(expression: GroupingNode): Any
    fun visitBinaryExp(expression: BinaryNode): Any
    fun visitUnaryExp(expression: UnaryNode): Any
    fun visitAssignment(expression: Assignation): Any
    fun visitCallExp(expression: CallNode): Any
    fun visitIdentifier(expression: Identifier): Any
}