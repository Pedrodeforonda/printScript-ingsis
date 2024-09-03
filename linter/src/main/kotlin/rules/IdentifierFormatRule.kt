package main.kotlin.rules

import Token
import main.kotlin.identifierFormats.IdentifierFormat

class IdentifierFormatRule(private val identifierFormat: IdentifierFormat) : LinterRule {

    override fun lintCode(token: Token): List<String> {
        val errors = mutableListOf<String>()
        val tokens = listOf(token)
        val tokenIterator = tokens.iterator()
        for (token in tokenIterator) {
            if (token.getType() == TokenType.IDENTIFIER) {
                errors.addAll(checkFormatErrors(token))
            }
        }
        return errors
    }

    private fun checkFormatErrors(token: Token): List<String> {
        val errors = mutableListOf<String>()
        // if the identifier token does not match the pattern of the identifier format
        if (!identifierFormat.getPattern().matcher(token.getCharArray()).matches()) {
            errors.add(
                "Invalid identifier: ${token.getCharArray()}" +
                    " at line ${token.getPosition().getLine()} column ${token.getPosition().getColumn()}",
            )
        }
        return errors
    }
}
