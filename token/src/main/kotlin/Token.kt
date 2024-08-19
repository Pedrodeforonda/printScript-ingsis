class Token(
    private val string: String,
    private val type: TokenType
) {
    fun getCharArray(): String {
        return string
    }

    fun getType(): TokenType {
        return type
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Token) return false

        if (string != other.string) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}