package visitors

import dataObjects.StringResult
import nodes.Node

class GetStringVisitor : ExpressionVisitor {
    override fun visitReadEnv(expression: Node): StringResult {
        return StringResult("ReadEnv")
    }

    override fun visitIf(expression: Node): StringResult {
        return StringResult("IfNode")
    }

    override fun visitDeclaration(expression: Node): StringResult {
        return StringResult("Declaration")
    }

    override fun visitLiteral(expression: Node): StringResult {
        return StringResult("Literal")
    }

    override fun visitBinaryExp(expression: Node): StringResult {
        return StringResult("BinaryNode")
    }

    override fun visitAssignment(expression: Node): StringResult {
        return StringResult("Assignation")
    }

    override fun visitCallExp(expression: Node): StringResult {
        return StringResult("CallNode")
    }

    override fun visitIdentifier(expression: Node): StringResult {
        return StringResult("Identifier")
    }

    override fun visitReadInput(expression: Node): StringResult {
        return StringResult("ReadInput")
    }
}
