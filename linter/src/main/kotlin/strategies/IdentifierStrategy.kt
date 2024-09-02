package main.kotlin.strategies

interface IdentifierStrategy {
    fun checkIdentifier(identifier: String): Boolean
    fun getIdentifierType(): String
}
