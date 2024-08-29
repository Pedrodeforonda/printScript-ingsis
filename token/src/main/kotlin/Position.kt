class Position(private val line: Int, private val column: Int) {
    fun nextColumn(): Position {
        return Position(1, column + 1)
    }
    fun nextLine(): Position {
        return Position(line + 1, column)
    }

    fun getLine(): Int {
        return line
    }

    fun getColumn(): Int {
        return column
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Position

        if (line != other.line) return false
        if (column != other.column) return false

        return true
    }

    override fun hashCode(): Int {
        var result = line
        result = 31 * result + column
        return result
    }

    override fun toString(): String {
        return "Position{" +
            "line=$line, " +
            "column=$column" +
            "}"
    }
}
