package utils

import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.IfNode
import nodes.Literal
import nodes.ReadEnv
import nodes.ReadInput

class GetStringVisitor : ExpressionVisitor {
    override fun visitReadEnv(expression: ReadEnv): StringResult {
        TODO("Not yet implemented")
    }

    override fun visitIf(expression: IfNode): StringResult {
        TODO("Not yet implemented")
    }

    override fun visitDeclaration(expression: Declaration): StringResult {
        return StringResult("Declaration")
    }

    override fun visitLiteral(expression: Literal): StringResult {
        return StringResult("Literal")
    }

    override fun visitBinaryExp(expression: BinaryNode): StringResult {
        return StringResult("BinaryNode")
    }

    override fun visitAssignment(expression: Assignation): StringResult {
        return StringResult("Assignation")
    }

    override fun visitCallExp(expression: CallNode): StringResult {
        return StringResult("CallNode")
    }

    override fun visitIdentifier(expression: Identifier): StringResult {
        return StringResult("Identifier")
    }

    override fun visitReadInput(expression: ReadInput): StringResult {
        return StringResult("ReadInput")
    }
}
