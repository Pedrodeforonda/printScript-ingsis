package org.example.nodes

import org.example.DeclarationKeyWord
import org.example.visitors.ExpressionVisitor

class Declaration(private val name: String, private val type: String, private val declarationKeyWord: DeclarationKeyWord): Node {
    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitDeclaration(this)
    }

    fun getName(): String {
        return name
    }

    fun getType(): String {
        return type
    }

    fun getDeclarationKeyWord(): DeclarationKeyWord {
        return declarationKeyWord
    }
}