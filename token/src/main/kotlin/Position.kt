class Position(val line: Int, val column: Int) {
    fun nextColumn(): Position {
        return Position(1, column + 1)
    }
    fun nextLine(): Position {
        return Position(line + 1, column)
    }

}