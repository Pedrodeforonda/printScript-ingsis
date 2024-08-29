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
}
