package main.kotlin

import Token
import main.kotlin.identifierFormats.IdentifierFormats
import main.kotlin.rules.IdentifierFormatRule
import main.kotlin.rules.PrintlnRestrictionRule

class Linter(private val tokens: Iterator<Token>) {

    private var currentToken: Token = tokens.next()

    fun lint(config: LinterConfig): List<String> {
        val errors = mutableListOf<String>()
        val identifierFormat = IdentifierFormats().formats.find { it.getFormat() == config.identifierFormat }

        while (tokens.hasNext()) {
            if (identifierFormat != null) {
                if (currentToken.getType() == TokenType.IDENTIFIER) {
                    errors.addAll(IdentifierFormatRule(identifierFormat).lintCode(currentToken))
                }
            }
            else if (config.restrictPrintln) {
                if (currentToken.getType() == TokenType.CALL_FUNC && currentToken.getCharArray() == "println") {
                    if (currentToken.getType() == TokenType.LEFT_PAREN) {
                        currentToken = tokens.next()
                        while (currentToken.getType() != TokenType.RIGHT_PAREN) {

                        }
                        errors.addAll(PrintlnRestrictionRule().lintCode(currentToken))
                    }
                }
            }
        }
        return errors
    }

    private fun goToNextToken(): Token {
        try {
            currentToken = tokens.next()
        } catch (e: NoSuchElementException) {
            throw RuntimeException("Unexpected end of input")
        }
        return currentToken
    }
}
