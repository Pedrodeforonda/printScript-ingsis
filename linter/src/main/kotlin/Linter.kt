package main.kotlin

import Token
import TokenType
import main.kotlin.rules.LinterRules

class Linter {

    fun lint(tokens: Sequence<Token>, config: LinterConfig): List<String> {
        val errors = mutableListOf<String>()
        var insidePrintln = false
        var isFirstTokenAfterPrintln = false

        tokens.forEach { token ->
            if (token.getType() == TokenType.CALL_FUNC && token.getText() == "println") {
                insidePrintln = true
                isFirstTokenAfterPrintln = true
                return@forEach // Skip the println token
            }
            if (token.getType() == TokenType.RIGHT_PAREN && insidePrintln) {
                insidePrintln = false
                isFirstTokenAfterPrintln = false
            }

            for (rule in LinterRules().rules) {
                errors.addAll(rule.lintCode(token, config))
            }

            if (insidePrintln) {
                if (isFirstTokenAfterPrintln) {
                    if (token.getType() == TokenType.LEFT_PAREN) {
                        isFirstTokenAfterPrintln = false
                        return@forEach // Skip the left paren token from the println
                    } else throw RuntimeException("Unexpected token after println: ${token.getText()}")
                }
                if (config.restrictPrintln && printlnRestrictionIsNotFollowed(token)) {
                    errors.add(
                        "Invalid println: ${token.getText()}" +
                                " at line ${token.getPosition().getLine()} column ${token.getPosition().getColumn()}"
                    )
                }
            }
        }

        return errors
    }

    private fun printlnRestrictionIsNotFollowed(token: Token): Boolean {
        return token.getType() != TokenType.STRING_LITERAL && token.getType() != TokenType.NUMBER_LITERAL &&
                token.getType() != TokenType.IDENTIFIER
    }
}
