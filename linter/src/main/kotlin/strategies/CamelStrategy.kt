package main.kotlin.strategies

import java.util.regex.Pattern

class CamelStrategy : IdentifierStrategy {
    private val camelCasePattern = Pattern.compile("^[a-z]+([A-Z][a-z]*)*$")

    override fun checkIdentifier(identifier: String): Boolean {
        return camelCasePattern.matcher(identifier).matches()
    }
}
