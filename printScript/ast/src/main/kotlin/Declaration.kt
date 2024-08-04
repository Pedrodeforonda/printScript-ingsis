package org.example

class Declaration(val name: String, val type: String, val declarationKeyWord: DeclarationKeyWord): Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitDeclaration(this)
    }
}