package main.kotlin.strategies

import java.util.regex.Pattern

class SnakeStrategy : IdentifierStrategy {
    private val snakeCasePattern = Pattern.compile("^[a-z]+(_[a-z]+)*$")

    override fun checkIdentifier(identifier: String): Boolean {
        return snakeCasePattern.matcher(identifier).matches()
    }

    override fun getIdentifierType(): String {
        return "snake_case"
    }
}
