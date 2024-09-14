package nodes

import types.LiteralType
import visitors.ExpressionVisitor

sealed class LiteralValue {
    data class StringValue(val value: String) : LiteralValue()
    data class NumberValue(val value: Number) : LiteralValue()
    data class BooleanValue(val value: Boolean) : LiteralValue()
}

class Literal private constructor(
    private val value: LiteralValue,
    private val pos: Position,
    private val type: LiteralType,
) : Node {

    constructor(value: String, pos: Position, type: LiteralType) : this(LiteralValue.StringValue(value), pos, type)
    constructor(value: Number, pos: Position, type: LiteralType) : this(LiteralValue.NumberValue(value), pos, type)
    constructor(value: Boolean, pos: Position, type: LiteralType) : this(LiteralValue.BooleanValue(value), pos, type)

    override fun accept(visitor: ExpressionVisitor): Any {
        return visitor.visitLiteral(this)
    }

    fun getValue(): LiteralValue {
        return value
    }

    fun getPos(): Position {
        return pos
    }

    fun getType(): LiteralType {
        return type
    }

    fun isString(): Boolean {
        return value is LiteralValue.StringValue
    }

    fun isNumber(): Boolean {
        return value is LiteralValue.NumberValue
    }

    fun isBoolean(): Boolean {
        return value is LiteralValue.BooleanValue
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
