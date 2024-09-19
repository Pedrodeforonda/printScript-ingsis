package nodes

import types.DeclarationType
import visitors.ExpressionVisitor

class Declaration(
    private val name: String,
    private val type: String,
    private val declarationKeyWord: DeclarationType,
    private val pos: Position,
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

    fun getDeclarationKeyWord(): DeclarationType {
        return declarationKeyWord
    }

    fun getPos(): Position {
        return pos
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

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + declarationKeyWord.hashCode()
        result = 31 * result + pos.hashCode()
        return result
    }
}
