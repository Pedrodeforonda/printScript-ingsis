package main

class Token(
    private val string: String,
    private val type: TokenType,
    private val position: Position,
) {
    fun getText(): String {
        return string
    }

    fun getType(): TokenType {
        return type
    }

    fun getPosition(): Position {
        return position
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Token

        if (string != other.string) return false
        if (type != other.type) return false
        if (position != other.position) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }

    override fun toString(): String {
        return "main.Token{" +
            "string='$string', " +
            "type=$type, " +
            "position=$position" +
            "}"
    }
}
