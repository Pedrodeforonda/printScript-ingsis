package main.kotlin.rules

import Token
import main.kotlin.LinterConfig
import main.kotlin.identifierFormats.IdentifierFormat
import main.kotlin.identifierFormats.IdentifierFormats

class IdentifierFormatRule() : LinterRule {

    override fun lintCode(token: Token, config: LinterConfig): List<String> {
        val errors = mutableListOf<String>()
        if (token.getType() == TokenType.IDENTIFIER) {
            val identifierFormat = IdentifierFormats().formats.find { it.getFormat() == config.identifierFormat }
            if (identifierFormat != null) {
                    errors.addAll(checkFormatErrors(token, identifierFormat))
            }
        }
        return errors
    }

    private fun checkFormatErrors(token: Token, identifierFormat: IdentifierFormat): List<String> {
        val errors = mutableListOf<String>()
        // if the identifier token does not match the pattern of the identifier format
        if (!identifierFormat.getPattern().matcher(token.getText()).matches()) {
            errors.add(
                "Invalid identifier: ${token.getText()}" +
                    " at line ${token.getPosition().getLine()} column ${token.getPosition().getColumn()}",
            )
        }
        return errors
    }
}
