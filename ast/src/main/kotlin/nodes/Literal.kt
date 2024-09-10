package nodes

import main.Position
import main.TokenType
import utils.ExpressionVisitor

class Literal private constructor(
    private val value: Any,
    private val pos: Position,
    private val type: TokenType,
) : Node {

    constructor(value: String, pos: Position, type: TokenType) : this(value as Any, pos, type)
    constructor(value: Number, pos: Position, type: TokenType) : this(value as Any, pos, type)
    constructor(value: Boolean, pos: Position, type: TokenType) : this(value as Any, pos, type)

    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitLiteral(this)
    }

    fun getValue(): Any {
        return value
    }

    fun getPos(): Position {
        return pos
    }

    fun getType(): TokenType {
        return type
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Literal) return false

        if (value != other.value) return false
        if (type != other.type) return false
        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + pos.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}
