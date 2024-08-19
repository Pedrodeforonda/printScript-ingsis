package nodes

import DeclarationKeyWord
import ExpressionVisitor

class Declaration(
    private val name: String,
    private val type: String,
    private val declarationKeyWord: DeclarationKeyWord
) : Node {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Declaration

        if (name != other.name) return false
        if (type != other.type) return false
        if (declarationKeyWord != other.declarationKeyWord) return false

        return true
    }
}
