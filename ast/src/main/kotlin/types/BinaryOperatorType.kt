package types

enum class BinaryOperatorType {
    PLUS,
    MINUS,
    SLASH,
    ASTERISK,
    ;

    fun isSum(): Boolean {
        return this == PLUS
    }
}
