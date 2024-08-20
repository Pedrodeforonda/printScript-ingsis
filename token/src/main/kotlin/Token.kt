class Token(
    private val charArray: CharArray,
    private val type: TokenType,
) {
    fun getCharArray(): CharArray {
        return charArray
    }

    fun getType(): TokenType {
        return type
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Token) return false

        if (!charArray.contentEquals(other.charArray)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = charArray.contentHashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}
