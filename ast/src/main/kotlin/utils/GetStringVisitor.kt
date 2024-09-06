package utils

import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal
import nodes.ReadInput

class GetStringVisitor : ExpressionVisitor {
    override fun visitDeclaration(expression: Declaration): Any {
        return "Declaration"
    }

    override fun visitLiteral(expression: Literal): Any {
        return "Literal"
    }

    override fun visitBinaryExp(expression: BinaryNode): Any {
        return "BinaryNode"
    }

    override fun visitAssignment(expression: Assignation): Any {
        return "Assignation"
    }

    override fun visitCallExp(expression: CallNode): Any {
        return "CallNode"
    }

    override fun visitIdentifier(expression: Identifier): Any {
        return "Identifier"
    }

    override fun visitReadInput(expression: ReadInput): Any {
        return "ReadInput"
    }
}
