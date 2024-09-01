package main.kotlin.rules

import Token
import org.example.Lexer
import java.util.regex.Pattern

class IdentifierFormatRule(private val identifierFormat: String) : LinterRule {

    private val camelCasePattern = Pattern.compile("^[a-z]+([A-Z][a-z]*)*$")
    private val snakeCasePattern = Pattern.compile("^[a-z]+(_[a-z]+)*$")

    override fun lintCode(input: String, lexer: Lexer): List<String> {
        val tokens = lexer.tokenizeAll(lexer)
        val errors = mutableListOf<String>()
        for (token in tokens) {
            if (token.getType() == TokenType.IDENTIFIER) {
                when (identifierFormat) {
                    "camelCase" -> errors.addAll(checkPattern(camelCasePattern, token))
                    "snake_case" -> errors.addAll(checkPattern(snakeCasePattern, token))
                }
            }
        }
        return errors
    }

    private fun checkPattern(pattern: Pattern, token: Token): List<String> {
        val errors = mutableListOf<String>()
        if (!pattern.matcher(token.getCharArray()).matches()) {
            errors.add(
                "Invalid identifier: ${token.getCharArray()}" +
                    " at line ${token.getPosition().getLine()} column ${token.getPosition().getColumn()}",
            )
        }
        return errors
    }
}
