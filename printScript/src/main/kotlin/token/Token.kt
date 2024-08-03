package token

class Token(
    private val charArray: CharArray,
    private val type: TokenType
) {
    fun getCharArray(): CharArray {
        return charArray
    }

    fun getType(): TokenType {
        return type
    }
}